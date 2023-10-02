package co.diegofer.inventoryclean.model.commands.custom;

public class LocationCommand {

    String city;
    String country;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLocation(){
        return city + ", " + country;
    }
}
