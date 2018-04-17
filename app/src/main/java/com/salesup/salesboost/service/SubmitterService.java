package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.GeoIP2Information;
import com.salesup.salesboost.domain.Submitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitterService {
    @Autowired
    private GeoIP2Service geoIP2Service;

    public Submitter createSubmitter(
            String submitterName, String submitterEmailAddress, String submitterPhoneNumber,
            String submitterCompanyName, String submitterTitle, String submitterIPAddress) {
        Submitter submitter = new Submitter();
        submitter.setName(submitterName);
        submitter.setEmailAddress(submitterEmailAddress);
        submitter.setPhoneNumber(submitterPhoneNumber);
        submitter.setCompanyName(submitterCompanyName);
        submitter.setTitle(submitterTitle);

        GeoIP2Information geoIP2Information = geoIP2Service.getInsights(submitterIPAddress);
        submitter.setGeoIP2Information(geoIP2Information);
        return submitter;
    }
}
