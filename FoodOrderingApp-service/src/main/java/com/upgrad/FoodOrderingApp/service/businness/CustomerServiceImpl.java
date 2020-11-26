/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private PasswordCryptographyProvider passwordCryptographyProvider;

  /*@Override
  public SearchResult<CustomerEntity> findUsers(int page, int limit) {
    return customerDao.findUsers(page, limit);
  }*/

  /*@Override
  public SearchResult<CustomerEntity> findUsers(UserStatus userStatus, int page, int limit) {
    return customerDao.findUsers(userStatus, page, limit);
  }*/

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByEmail(final String emailAddress) {

    final CustomerEntity CustomerEntity = customerDao.findByEmail(emailAddress);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_002, emailAddress);
    }
    return CustomerEntity;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByUuid(final String userUuid) {

    final CustomerEntity CustomerEntity = customerDao.findByUUID(userUuid);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }
    return CustomerEntity;
  }

  /*@Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity createUser(final CustomerEntity newUser, final Integer roleUuid)
      throws ApplicationException {

    final CustomerEntity existingUser = customerDao.findByEmail(newUser.getEmail());
    if (existingUser != null) {
      throw new ApplicationException(UserErrorCode.USR_009, newUser.getEmail());
    }
    encryptPassword(newUser);
    newUser.setSubscriptionsConsent(true);
    newUser.setRole(roleService.findRoleByUuid(roleUuid));

    return customerDao.create(newUser);
  }*/

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity createCustomer(final CustomerEntity newUser) {

    final CustomerEntity CustomerEntity = customerDao.findByEmail(newUser.getEmail());
    if (CustomerEntity != null) {
      //throw new ApplicationException(UserErrorCode.USR_009, newUser.getEmail());
    }
    encryptPassword(newUser);
    return customerDao.create(newUser);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateCustomer(final String userUuid, final CustomerEntity updatedUser) {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    /*if (UserStatus.DELETED == UserStatus.valueOf(existingUser.getStatus())) {
      throw new ApplicationException(UserErrorCode.USR_012, userUuid);
    }*/

    if (!existingUser.getEmail().equalsIgnoreCase(updatedUser.getEmail())
        && customerDao.findByEmail(updatedUser.getEmail()) != null) {
      //throw new ApplicationException(UserErrorCode.USR_009, updatedUser.getEmail());
    }

    if (StringUtils.isNotEmpty(updatedUser.getFirstName())) {
      existingUser.setFirstName(updatedUser.getFirstName());
    }
    if (StringUtils.isNotEmpty(updatedUser.getLastName())) {
      existingUser.setLastName(updatedUser.getLastName());
    }
    if (StringUtils.isNotEmpty(updatedUser.getEmail())) {
      existingUser.setEmail(updatedUser.getEmail());
    }
    if (StringUtils.isNotEmpty(updatedUser.getContact_number())) {
      existingUser.setContact_number(updatedUser.getContact_number());
    }
    customerDao.update(existingUser);
  }

  /*
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void changeUserStatus(final String userUuid, final UserStatus newUserStatus)
      throws ApplicationException {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    if (UserStatus.valueOf(existingUser.getStatus()) != newUserStatus) {
      existingUser.setStatus(newUserStatus.name());
      customerDao.update(existingUser);
    }
  }*/

  @Override
  public void deleteCustomer(final String userUuid) {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    //existingUser.setStatus(UserStatus.DELETED.name());
    customerDao.update(existingUser);
  }

  private void encryptPassword(final CustomerEntity newUser) {

    String password = newUser.getPassword();
    if (password == null) {
      password = "foodapp@123";
    }

    final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
    newUser.setSalt(encryptedData[0]);
    newUser.setPassword(encryptedData[1]);
  }

}