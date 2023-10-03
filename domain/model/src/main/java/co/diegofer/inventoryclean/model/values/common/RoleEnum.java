package co.diegofer.inventoryclean.model.values.common;

public enum RoleEnum {
    ADMIN,USER;

    public static boolean contains(String role) {
        for (RoleEnum r : values()) {
            if (r.name().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
