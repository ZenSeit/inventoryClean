package co.diegofer.inventoryclean.api.config;

import io.swagger.v3.oas.annotations.media.Schema;

public class BranchCommandSw {

    private String name;

    @Schema(implementation = LocationSw.class)
    private LocationSw location;

    public BranchCommandSw() {
    }



    public String getName() {
        return name;
    }


    public LocationSw getLocation() {
        return location;
    }
}
