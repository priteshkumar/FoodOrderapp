package com.upgrad.FoodOrderingApp.api.controller.transformer;

import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public final class AddressTransformer {

  public static AddressEntity toEntity(SaveAddressRequest saveAddressRequest) {
    AddressEntity addressEntity = new AddressEntity();
    if (!StringUtils.isEmpty(saveAddressRequest.getFlatBuildingName())) {
      addressEntity.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
    }
    if (!StringUtils.isEmpty(saveAddressRequest.getCity())) {
      addressEntity.setCity(saveAddressRequest.getCity());
    }
    if (!StringUtils.isEmpty(saveAddressRequest.getLocality())) {
      addressEntity.setLocality(saveAddressRequest.getLocality());
    }
    if (!StringUtils.isEmpty(saveAddressRequest.getPincode())) {
      addressEntity.setPincode(saveAddressRequest.getPincode());
    }
    addressEntity.setUuid(UUID.randomUUID().toString());
    addressEntity.setActive(1);
    return addressEntity;
  }

  public static SaveAddressResponse toSaveAddressResponse(AddressEntity addressEntity) {
    SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(addressEntity.getUuid())
        .status("ADDRESS SUCCESSFULLY REGISTERED");
    return saveAddressResponse;
  }
}
