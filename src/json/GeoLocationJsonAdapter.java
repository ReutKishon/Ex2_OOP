package json;

import api.*;
import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;

public class GeoLocationJsonAdapter implements JsonSerializer<Point3D>, JsonDeserializer<Point3D> {


    @Override
    public Point3D deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return new Point3D(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Point3D point3D, Type type, JsonSerializationContext jsonSerializationContext) {
        var locationStr = "" + point3D.x() + "," + point3D.y() + "," + point3D.z();
        return new JsonPrimitive(locationStr);
    }
}
