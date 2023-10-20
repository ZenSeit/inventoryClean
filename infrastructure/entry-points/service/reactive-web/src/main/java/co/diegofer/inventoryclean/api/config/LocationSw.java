package co.diegofer.inventoryclean.api.config;

import io.swagger.v3.oas.annotations.media.Schema;

public class LocationSw {

    @Schema(description = "City")
    String city;
    @Schema(description = "country")
    String country;

    public LocationSw() {
    }

    public LocationSw(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


}
