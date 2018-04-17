package com.salesup.salesboost.domain;

import com.salesup.salesboost.config.jpa.JpaConverterJson;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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
    // GeoIP2Information
    @Convert(converter = JpaConverterJson.class)
    @Lob
    private List<GeoIP2Information> geoIP2InformationList;

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

    public List<GeoIP2Information> getGeoIP2InformationList() {
        return geoIP2InformationList;
    }

    public void setGeoIP2InformationList(List<GeoIP2Information> geoIP2InformationList) {
        this.geoIP2InformationList = geoIP2InformationList;
    }

    public void addGeoIP2InformationList(GeoIP2Information geoIP2Information) {
        if (geoIP2InformationList == null || geoIP2InformationList.isEmpty()) {
            geoIP2InformationList = new LinkedList<>();
        }
        geoIP2InformationList.add(geoIP2Information);
    }
}
