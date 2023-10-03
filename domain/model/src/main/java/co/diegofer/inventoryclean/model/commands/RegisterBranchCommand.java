package co.diegofer.inventoryclean.model.commands;

import co.diegofer.inventoryclean.model.commands.custom.LocationCommand;
import co.diegofer.inventoryclean.model.generic.Command;

public class RegisterBranchCommand extends Command {

    private String name;
    private LocationCommand location;

    public RegisterBranchCommand() {
    }

    public RegisterBranchCommand(String name, LocationCommand location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }


    public String getLocation() {
        return location.getLocation();
    }

}
