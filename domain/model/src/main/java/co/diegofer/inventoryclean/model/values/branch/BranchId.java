package co.diegofer.inventoryclean.model.values.branch;

import co.diegofer.inventoryclean.model.generic.Identity;

public class BranchId extends Identity {

    public BranchId(){
    }

    private BranchId(String uuid){
        super(uuid);
    }

    public static BranchId of(String uuid){
        return new BranchId(uuid);
    }

}
