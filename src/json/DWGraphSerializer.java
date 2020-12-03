package json;

import api.edge_data;
import api.node_data;
import com.google.gson.*;
import graph_implementation.DWGraph_DS;
import graph_implementation.Edge;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DWGraphSerializer implements JsonSerializer<DWGraph_DS> {

    @Override
    public JsonElement serialize(DWGraph_DS dwGraphDs, Type type, JsonSerializationContext jsonSerializationContext) {
        var dwGraphJson = new JsonObject();

        var edges = new ArrayList<edge_data>();

        for (node_data node : dwGraphDs.getV()) {
            edges.addAll(dwGraphDs.getE(node.getKey()));
        }


        var edgesJsonElement = new Gson().toJsonTree(edges);
        dwGraphJson.add("Edges", edgesJsonElement);


        return dwGraphJson;
    }
}
