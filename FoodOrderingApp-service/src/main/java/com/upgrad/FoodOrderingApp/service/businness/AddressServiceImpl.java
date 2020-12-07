package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
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
    customerAddressEntity.setCustomerEntity(customerEntity);
    customerAddressEntity.setAddressEntity(addressEntity);
    AddressEntity createdAddress = addressDao.saveAddress(addressEntity);
    customerAddressDao.create(customerAddressEntity);
    return createdAddress;
  }

  private boolean verifyAddressFields(AddressEntity addressEntity) {
    if (StringUtils.isEmpty(addressEntity.getCity()) || StringUtils
        .isEmpty(addressEntity.getFlat_buil_number()) || StringUtils
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
