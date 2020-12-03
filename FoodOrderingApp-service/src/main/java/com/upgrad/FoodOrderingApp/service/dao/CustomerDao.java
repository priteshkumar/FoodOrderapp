package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Repository bean for CustomerEntity
 */
@Repository
public class CustomerDao {

  @PersistenceContext
  EntityManager entityManager;

  public CustomerEntity create(CustomerEntity CustomerEntity) {
    entityManager.persist(CustomerEntity);
    return CustomerEntity;
  }

  public void deleteUser(CustomerEntity CustomerEntity) {
    entityManager.remove(CustomerEntity);
  }
  /*
  public CustomerEntity getUserByUserName(String userName) {
    try {
      return entityManager.createNamedQuery("userByUserName", CustomerEntity.class).setParameter(
          "userName", userName).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }*/

  public CustomerEntity findByContactNumber(String contactNumber) {
    try {
      return entityManager.createNamedQuery(CustomerEntity.BY_CONTACTNUMBER, CustomerEntity.class).setParameter(
          "contactNumber", contactNumber).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public CustomerEntity findByUUID(String uuid) {
    try {
      return entityManager.createNamedQuery("customerByUuid", CustomerEntity.class).setParameter(
          "uuid", uuid).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
    return entityManager.merge(customerEntity);
  }
}
