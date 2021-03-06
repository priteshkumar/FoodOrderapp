package com.upgrad.FoodOrderingApp.service.entity;

import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_ALL_STATES;
import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_STATE_UUID;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
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
        query = "SELECT st FROM StateEntity st WHERE st.uuid = :uuid"),
    @NamedQuery(name = BY_ALL_STATES, //
        query = "SELECT st FROM StateEntity st")
})

@javax.persistence.Entity
@Table(name = "state", schema = "public")
public class StateEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String>, Serializable {

  public static final String BY_STATE_UUID = "StateEntity.byUUID";
  public static final String BY_ALL_STATES = "StateEntity.byAllStates";

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

  public StateEntity() {
  }

  public StateEntity(String stateUuid, String state) {
    this.uuid = stateUuid;
    this.state_name = state;
  }

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
