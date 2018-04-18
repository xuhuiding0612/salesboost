package com.salesup.salesboost.service;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.InsightsResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;
import com.salesup.salesboost.domain.QueryGeolocation;
import com.salesup.salesboost.exception.RequestBodyValidationException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/** */
@Service
@PropertySource("classpath:application.properties")
public class GeoIP2Service {
  @Value("${GeoIP2.accountId}")
  private Integer geoIP2AccountId;

  @Value("${GeoIP2.licenseKey}")
  private String geoIP2LicenseKey;

  /**
   * getInsights method mainly uses GeoIP2 library. Insights response get from GeoIP2 api includes
   * query information: country, subdivision, city, postal, location
   *
   * @param ipAddressString IPv4 address string for querying geolocation information.
   * @return Queried geolocation of provided ip address.
   */
  public QueryGeolocation getInsights(String ipAddressString) {
    QueryGeolocation queryGeolocation = new QueryGeolocation();
    queryGeolocation.setQueryTime(new Date());
    try (WebServiceClient client =
        new WebServiceClient.Builder(geoIP2AccountId, geoIP2LicenseKey).build()) {

      InetAddress ipAddress = InetAddress.getByName(ipAddressString);

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

      queryGeolocation.setCity(city);
      queryGeolocation.setCountry(country);
      queryGeolocation.setSubdivision(subdivision);
      queryGeolocation.setPostal(postal);
      queryGeolocation.setLocation(location);
      return queryGeolocation;
    } catch (IOException | GeoIp2Exception e) {
      throw new RequestBodyValidationException(
          "Error occurred during getting QueryGeolocation by ip address: " + e.getMessage());
    }
  }
}
