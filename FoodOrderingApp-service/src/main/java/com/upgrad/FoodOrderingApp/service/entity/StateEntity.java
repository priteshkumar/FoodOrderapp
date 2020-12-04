package com.upgrad.FoodOrderingApp.service.entity;
/*
DROP TABLE IF EXISTS STATE CASCADE;
CREATE TABLE STATE(id SERIAL,
uuid VARCHAR(200) UNIQUE NOT NULL,
state_name VARCHAR(30),PRIMARY KEY (id));
 */

import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_STATE_UUID;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@NamedQueries({
    @NamedQuery(name = BY_STATE_UUID, //
        query = "SELECT st FROM StateEntity st WHERE st.uuid = :uuid")
})

@javax.persistence.Entity
@Table(name = "state", schema = "public")
public class StateEntity implements Entity, Identifier<Integer>, UniversalUniqueIdentifier<String> {

  public static final String BY_STATE_UUID = "StateEntity.byUUID";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "state_name")
  @Size(max = 30)
  private String state_name;

  public String getState_name() {
    return state_name;
  }

  public void setState_name(String state_name) {
    this.state_name = state_name;
  }

  @Override
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getUuid() {
    return this.uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public boolean equals(Object obj) {
    return new EntityEqualsBuilder<Integer>().equalsById(this, obj);
  }

  @Override
  public int hashCode() {
    return new EntityHashCodeBuilder<Integer>().hashCodeById(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
