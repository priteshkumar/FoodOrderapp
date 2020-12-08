package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private AddressDao addressDao;

  @Autowired
  private CustomerAddressDao customerAddressDao;

  @Override
  public StateEntity getStateByUUID(@NotNull String uuid) throws AddressNotFoundException {
    StateEntity stateEntity = addressDao.getStateByUUID(uuid);
    if (stateEntity == null) {
      throw new AddressNotFoundException("ANF-002", "No state by this state id");
    }
    return stateEntity;
  }

  @Override
  public AddressEntity getAddressByUUID(final String uuid,
      @NotNull CustomerEntity customerEntity)
      throws AddressNotFoundException, AuthorizationFailedException {

    if (StringUtils.isEmpty(uuid) == true) {
      throw new AddressNotFoundException("ANF-005", "Address id can not be empty");
    }

    AddressEntity addressEntity = addressDao.findByUUID(uuid);
    if (Objects.equals(addressEntity, null) == true) {
      throw new AddressNotFoundException("ANF-003", "No address by this id");
    }
    CustomerAddressEntity customerAddressEntity =
        customerAddressDao.findByAddressAndCustomer(addressEntity.getId(), customerEntity.getId());
    if (Objects.equals(customerAddressEntity, null) == true) {
      throw new AuthorizationFailedException("ATHR-004",
          "You are not authorized to view/update/delete any one else's address");
    }
    return addressEntity;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public AddressEntity saveAddress(@NotNull AddressEntity addressEntity,
      @NotNull CustomerEntity customerEntity) throws SaveAddressException {
    if (!verifyAddressFields(addressEntity)) {
      throw new SaveAddressException("SAR-001", "No field can be empty");
    }
    if (!verifyPinCode(addressEntity.getPincode())) {
      throw new SaveAddressException("SAR-002", "Invalid pincode");
    }
    CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
    customerAddressEntity.setCustomer(customerEntity);
    customerAddressEntity.setAddress(addressEntity);
    AddressEntity createdAddress = addressDao.saveAddress(addressEntity);
    customerAddressDao.create(customerAddressEntity);
    return createdAddress;
  }

  @Override
  public List<AddressEntity> getAllAddress(@NotNull CustomerEntity customerEntity) {
    List<CustomerAddressEntity> customerAddressEntities =
        customerAddressDao.getAllCustomerAddress(customerEntity.getId());
    List<AddressEntity> addressEntities = Optional.ofNullable(customerAddressEntities)
        .map(List::stream).orElseGet(Stream::empty)
        .map(CustomerAddressEntity::getAddress)
        .collect(Collectors.toList());
    return addressEntities;
  }

  @Override
  public List<StateEntity> getAllStates() {
    return addressDao.getAllStates();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public AddressEntity deleteAddress(@NotNull AddressEntity addressEntity) {
    return addressDao.delete(addressEntity);
  }


  private boolean verifyAddressFields(AddressEntity addressEntity) {
    if (StringUtils.isEmpty(addressEntity.getCity()) || StringUtils
        .isEmpty(addressEntity.getFlatBuilNo()) || StringUtils
        .isEmpty(addressEntity.getLocality()) || org.springframework.util.StringUtils
        .isEmpty(addressEntity.getPincode())) {
      return false;
    }
    return true;
  }

  private boolean verifyPinCode(String pinCode) {
    String pincodePattern = "^[0-9]{6}$";
    if (Pattern.matches(pincodePattern, pinCode)) {
      return true;
    }
    return false;
  }
}
