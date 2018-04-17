package com.salesup.salesboost.exception;

public class GeoIP2Exception extends Exception{

    public GeoIP2Exception() {
        super("Error occurred  during getting Geolocation by ip address.");
    }
}
