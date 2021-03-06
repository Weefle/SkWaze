package fr.weefle.waze.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializableManager {

    private Gson gson;

    public SerializableManager() {
        this.gson = createGsonInstance();
    }

    public Gson createGsonInstance(){
        return new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    }

    public String serialize(Object object) {

        return this.gson.toJson(object);
    }

    public Object deserialize(String string) {

        return this.gson.fromJson(string, Object.class);
    }
}
