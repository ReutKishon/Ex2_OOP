package json;

import api.edge_data;
import api.geo_location;
import api.node_data;
import com.google.gson.*;
import gameClient.util.Point3D;
import api.DWGraph_DS;
import api.Edge;
import api.Node;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DWGraphJsonAdapter implements JsonSerializer<DWGraph_DS>, JsonDeserializer<DWGraph_DS> {


    private final GsonBuilder gsonBuilder;

    public DWGraphJsonAdapter() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(geo_location.class, (InstanceCreator<geo_location>) type -> new Point3D(0, 0, 0));
        gsonBuilder.registerTypeAdapter(geo_location.class, new GeoLocationJsonAdapter());


    }

    @Override
    public JsonElement serialize(DWGraph_DS dwGraphDs, Type type, JsonSerializationContext jsonSerializationContext) {
        var dwGraphJson = new JsonObject();


        var edges = new ArrayList<edge_data>();

        for (node_data node : dwGraphDs.getV()) {
            edges.addAll(dwGraphDs.getE(node.getKey()));
        }

        var nodes = new ArrayList<>(dwGraphDs.getV());

        var edgesJsonElement = gsonBuilder.create().toJsonTree(edges);
        var nodesJsonElement = gsonBuilder.create().toJsonTree(nodes);

        dwGraphJson.add("Edges", edgesJsonElement);
        dwGraphJson.add("Nodes", nodesJsonElement);


        return dwGraphJson;
    }

    @Override
    public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject dwGraphJson = jsonElement.getAsJsonObject();

        var edges = gsonBuilder.create().fromJson(dwGraphJson.get("Edges"), Edge[].class);
        var nodes = gsonBuilder.create().fromJson(dwGraphJson.get("Nodes"), Node[].class);


        var graphDw = new DWGraph_DS();
        for (node_data node : nodes) {
            node_data n = new Node(node.getKey(), node.getTag(), node.getLocation(), node.getWeight(), node.getInfo());
            graphDw.addNode(n);

        }

        for (edge_data edge : edges) {
            graphDw.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
        }


        return graphDw;
    }
}
