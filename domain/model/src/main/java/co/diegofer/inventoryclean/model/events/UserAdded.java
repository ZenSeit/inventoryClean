package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.generic.DomainEvent;

public class UserAdded extends DomainEvent {


    private String userId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public UserAdded() {
        super("co.diegofer.inventoryclean.model.events.UserCreated");
    }

    public UserAdded(String userId, String name, String lastName, String email, String password, String role) {
        super("co.diegofer.inventoryclean.model.events.UserCreated");
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
