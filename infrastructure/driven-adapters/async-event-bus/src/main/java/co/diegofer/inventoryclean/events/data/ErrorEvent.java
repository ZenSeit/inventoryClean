package co.diegofer.inventoryclean.events.data;


import co.diegofer.inventoryclean.model.generic.DomainEvent;

public class ErrorEvent extends DomainEvent {

    private final String classType;
    private final String message;

    public ErrorEvent(String classType, String message) {
        super("post.error");
        this.classType = classType;
        this.message = message;
    }

    public String getClassType() {
        return classType;
    }

    public String getMessage() {
        return message;
    }
}
