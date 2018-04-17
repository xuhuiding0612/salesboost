package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.QueryGeolocation;
import com.salesup.salesboost.domain.Submitter;
import com.salesup.salesboost.repository.SubmitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class SubmitterService {
  @Autowired private GeoIP2Service geoIP2Service;
  @Autowired private SubmitterRepository submitterRepository;

  /**
   * Create a submitter and save in database based on information of submitter passed in.
   *
   * @param submitterName Required field; submitter's name
   * @param submitterEmailAddress Required field: submitter's email address
   * @param submitterPhoneNumber Optional field: submitter's phone number
   * @param submitterCompanyName Optional field: submitter's company name
   * @param submitterTitle Optional field: submitter's title
   * @param submitterIPAddress Required field: submitter's IPv4 address when sent the request
   * @return New Submitter object.
   */
  public Submitter createSubmitter(
      String submitterName,
      String submitterEmailAddress,
      String submitterPhoneNumber,
      String submitterCompanyName,
      String submitterTitle,
      String submitterIPAddress) {
    Submitter submitter = new Submitter();
    submitter.setName(submitterName);
    submitter.setEmailAddress(submitterEmailAddress);
    submitter.setPhoneNumber(submitterPhoneNumber);
    submitter.setCompanyName(submitterCompanyName);
    submitter.setTitle(submitterTitle);

    // Query insights queryGeolocation information by IPv4 address via GeoIP2 api.
    QueryGeolocation queryGeolocation = geoIP2Service.getInsights(submitterIPAddress);
    submitter.addGeoIP2InformationList(queryGeolocation);

    return submitterRepository.save(submitter);
  }
}
