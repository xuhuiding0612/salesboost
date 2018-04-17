package com.salesup.salesboost.service;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.InsightsResponse;
import com.maxmind.geoip2.record.*;
import com.salesup.salesboost.domain.GeoIP2Information;
import com.salesup.salesboost.exception.GeoIP2Exception;

import java.io.IOException;
import java.net.InetAddress;

public class GeoIP2Service {
    public GeoIP2Information getInsights(String ipAddressString) {
        GeoIP2Information geoIP2Information = new GeoIP2Information();
        try (WebServiceClient client = new WebServiceClient.Builder(42, "license_key")
                .build()) {

            InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

            // Do the lookup
            InsightsResponse response = client.insights(ipAddress);

            Country country = response.getCountry();
//            System.out.println(country.getIsoCode());            // 'US'
//            System.out.println(country.getName());               // 'United States'
//            System.out.println(country.getNames().get("zh-CN")); // '美国'
//            System.out.println(country.getConfidence());         // 99

            Subdivision subdivision = response.getMostSpecificSubdivision();
//            System.out.println(subdivision.getName());       // 'Minnesota'
//            System.out.println(subdivision.getIsoCode());    // 'MN'
//            System.out.println(subdivision.getConfidence()); // 90

            City city = response.getCity();
//            System.out.println(city.getName());       // 'Minneapolis'
//            System.out.println(city.getConfidence()); // 50

            Postal postal = response.getPostal();
//            System.out.println(postal.getCode());       // '55455'
//            System.out.println(postal.getConfidence()); // 40

            Location location = response.getLocation();
//            System.out.println(location.getLatitude());        // 44.9733
//            System.out.println(location.getLongitude());       // -93.2323
//            System.out.println(location.getAccuracyRadius());  // 3
//            System.out.println(location.getTimeZone());        // 'America/Chicago'

//            System.out.println(response.getTraits().getUserType()); // 'college'

            geoIP2Information.setCity(city);
            geoIP2Information.setCountry(country);
            geoIP2Information.setSubdivision(subdivision);
            geoIP2Information.setPostal(postal);
            geoIP2Information.setLocation(location);
            return geoIP2Information;
        } catch (IOException | GeoIp2Exception e) {
            throw new RuntimeException("Error occurred during getting Geolocation by ip address: " + e.getMessage());
        }
    }
}
