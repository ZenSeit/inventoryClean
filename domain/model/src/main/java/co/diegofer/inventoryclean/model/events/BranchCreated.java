package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.generic.DomainEvent;

public class BranchCreated extends DomainEvent {

    private String name;
    private String location;
    public BranchCreated() {
        super("co.diegofer.inventoryclean.model.events.BranchCreated");
    }

    public BranchCreated(String name,String location){
        super("co.diegofer.inventoryclean.model.events.BranchCreated");
        this.name = name;
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public String getLocation(){
        return location;
    }

}
