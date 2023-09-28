package co.diegofer.inventoryclean.serializer;

public interface JSONMapper {
    String writeToJson(Object obj);
    Object readFromJson(String json, Class<?> clazz);
}
