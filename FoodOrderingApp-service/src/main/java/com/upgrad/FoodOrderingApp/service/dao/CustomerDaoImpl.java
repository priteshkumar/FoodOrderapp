/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: CustomerDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */

package com.upgrad.FoodOrderingApp.service.dao;
/*
import com.upgrad.FoodOrderingApp.service.dao.BaseDaoImpl;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDaoV1;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link CustomerDao}.

/*
@Repository
public class CustomerDaoImpl extends BaseDaoImpl<CustomerEntity> implements CustomerDaoV1 {

  @Override
  public CustomerEntity findByContactNumber(final String contactNumber) {
    try {
      return entityManager.createNamedQuery(CustomerEntity.BY_CONTACTNUMBER, CustomerEntity.class)
          .setParameter("contact_number", contactNumber).getSingleResult();
    } catch (NoResultException noResultExc) {
      return null;
    }
  }
    /*
    @Override
    public SearchResult<CustomerEntity> findCustomers(int page, int limit) {

        final int totalCount = entityManager.createNamedQuery(CustomerEntity.COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<CustomerEntity> payload = entityManager.createNamedQuery(CustomerEntity.BY_ALL, CustomerEntity.class).setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<CustomerEntity> findCustomers(CustomerStatus CustomerStatus, int page, int limit) {

        final int totalCount = entityManager.createNamedQuery(CustomerEntity.COUNT_BY_STATUS, Long.class)
                                            .setParameter("status", CustomerStatus.name())
                                            .getSingleResult().intValue();
        final List<CustomerEntity> payload = entityManager.createNamedQuery(CustomerEntity.BY_STATUS, CustomerEntity.class)
                                            .setParameter("status", CustomerStatus.name())
                                            .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

}*/