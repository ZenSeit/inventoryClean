package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.Identity;

public class UserId extends Identity {

    public UserId(){
    }

    private UserId(String uuid){
        super(uuid);
    }

    public static UserId of(String uuid){
        return new UserId(uuid);
    }
}

