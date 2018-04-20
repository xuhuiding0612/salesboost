package com.salesup.salesboost.domain;

import com.salesup.salesboost.config.jpa.JpaConverterJson;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Submitter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String emailAddress;
  private String phoneNumber;
  private String companyName;
  private String title;
  // QueriedGeolocation
  @Convert(converter = JpaConverterJson.class)
  @Lob
  private List<QueriedGeolocation> queriedGeolocationList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<QueriedGeolocation> getQueriedGeolocationList() {
    return queriedGeolocationList;
  }

  public void setQueriedGeolocationList(List<QueriedGeolocation> queriedGeolocationList) {
    this.queriedGeolocationList = queriedGeolocationList;
  }

  public void addGeoIP2InformationList(QueriedGeolocation queriedGeolocation) {
    if (queriedGeolocationList == null || queriedGeolocationList.isEmpty()) {
      queriedGeolocationList = new LinkedList<>();
    }
    queriedGeolocationList.add(queriedGeolocation);
  }
}
