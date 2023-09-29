package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.generic.Entity;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.user.Email;
import co.diegofer.inventoryclean.model.values.user.Password;
import co.diegofer.inventoryclean.model.values.user.Role;
import co.diegofer.inventoryclean.model.values.user.UserId;

public class UserEntity extends Entity<UserId> {

    private Name name;
    private Email email;
    private Password password;
    private Role role;

    public UserEntity(UserId id, Name name, Email email, Password password, Role role){
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Name name(){
        return name;
    }

    public Email email(){
        return email;
    }

    public Password password(){
        return password;
    }

    public Role role(){
        return role;
    }

}
