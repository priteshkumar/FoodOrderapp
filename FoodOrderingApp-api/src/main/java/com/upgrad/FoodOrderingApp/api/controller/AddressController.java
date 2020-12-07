package com.upgrad.FoodOrderingApp.api.controller;

import static com.upgrad.FoodOrderingApp.api.controller.transformer.AddressTransformer.toEntity;
import static com.upgrad.FoodOrderingApp.api.controller.transformer.AddressTransformer.toSaveAddressResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.api.model.AddressList;
import com.upgrad.FoodOrderingApp.api.model.AddressListResponse;
import com.upgrad.FoodOrderingApp.api.model.AddressListState;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AddressService addressService;

  @RequestMapping(method = POST, path = "/address", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SaveAddressResponse> saveAddress(
      @RequestHeader final String authorization,
      @RequestBody(required = false) final SaveAddressRequest saveAddressRequest)
      throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    final CustomerEntity customerEntity = customerService
        .getCustomer(bearerAuthDecoder.getAccessToken());
    final StateEntity stateEntity =
        addressService.getStateByUUID(saveAddressRequest.getStateUuid());
    final AddressEntity newAddress = toEntity(saveAddressRequest);
    newAddress.setState(stateEntity);
    final AddressEntity savedAddress = addressService.saveAddress(newAddress, customerEntity);
    return ResponseBuilder.created().payload(toSaveAddressResponse(savedAddress)).build();
  }

  @RequestMapping(method = GET, path = "/address/customer",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<AddressListResponse> getAllPermanentAddress(
      @RequestHeader final String authorization)
      throws AuthorizationFailedException {

    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    final CustomerEntity customerEntity = customerService
        .getCustomer(bearerAuthDecoder.getAccessToken());
    List<AddressEntity> addressEntityList = addressService.getAllAddress(customerEntity);
    return ResponseBuilder.ok().payload(toAddressListResponse(addressEntityList)).build();
  }

  private AddressListResponse toAddressListResponse(List<AddressEntity> addressEntities) {
    AddressListResponse addressListResponse = new AddressListResponse();

    List<AddressList> addressLists =
        Optional.ofNullable(addressEntities).map(List::stream).orElseGet(Stream::empty)
            .map(addressEntity -> {
              AddressList addressList =
                  new AddressList().id(UUID.fromString(addressEntity.getUuid()))
                      .flatBuildingName(addressEntity.getFlatBuilNo())
                      .locality(addressEntity.getLocality()).city(addressEntity.getCity())
                      .pincode(addressEntity.getPincode())
                      .state(new AddressListState()
                          .id(UUID.fromString(addressEntity.getState().getUuid()))
                          .stateName(addressEntity.getState().getState_name()));
              return addressList;
            }).collect(Collectors.toList());
    addressListResponse = addressListResponse.addresses(addressLists);
    return addressListResponse;
  }
}
