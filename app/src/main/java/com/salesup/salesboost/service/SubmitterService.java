package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.QueriedGeolocation;
import com.salesup.salesboost.domain.Submitter;
import com.salesup.salesboost.exception.ExceptionFactory;
import com.salesup.salesboost.exception.ExceptionType;
import com.salesup.salesboost.repository.SubmitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class SubmitterService {
  @Autowired private GeoIP2Service geoIP2Service;
  @Autowired private SubmitterRepository submitterRepository;
  @Autowired private UtilService utilService;

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
    // validating and setting submitter's full name
    if (submitterName != null && utilService.validateFullName(submitterName)) {
      submitter.setName(submitterName);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException,
          "Detected invalid submitter's full name");
    }

    // validating and setting submitter's email address
    if (submitterEmailAddress != null && utilService.validateEmailAddress(submitterEmailAddress)) {
      submitter.setEmailAddress(submitterEmailAddress);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException,
          "Detected invalid submitter's email address");
    }

    // validating and setting submitter's phone number
    if (submitterPhoneNumber == null || utilService.validatePhoneNumber(submitterPhoneNumber)) {
      submitter.setPhoneNumber(submitterPhoneNumber);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException,
          "Detected invalid submitter's phone number");
    }

    // validating and setting submitter's company name
    if (submitterCompanyName == null || utilService.validateCompanyName(submitterCompanyName)) {
      submitter.setCompanyName(submitterCompanyName);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException,
          "Detected invalid submitter's company name");
    }

    // validating and setting submitter's title
    if (submitterTitle == null || utilService.validateTitle(submitterTitle)) {
      submitter.setTitle(submitterTitle);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException, "Detected invalid submitter's title");
    }

    // validating and setting submitter's ip address
    if (submitterIPAddress != null) {
      // Query insights queriedGeolocation information by IPv4 address via GeoIP2 api.
      QueriedGeolocation queriedGeolocation = geoIP2Service.getInsights(submitterIPAddress);
      submitter.addGeoIP2InformationList(queriedGeolocation);
    } else {
      throw ExceptionFactory.create(
          ExceptionType.IllegalRequestBodyFieldsException,
          "Detected invalid submitter's ip address: empty");
    }

    return submitterRepository.save(submitter);
  }
}
