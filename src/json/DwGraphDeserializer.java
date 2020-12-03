package json;

import api.edge_data;
import com.google.gson.*;
import graph_implementation.DWGraph_DS;
import graph_implementation.Edge;
import graph_implementation.Node;

import java.lang.reflect.Type;

public class DwGraphDeserializer implements JsonDeserializer<DWGraph_DS> {

    @Override
    public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var dwGraphJson = jsonElement.getAsJsonObject();
        var edges = new Gson().fromJson(dwGraphJson.get("Edges"), Edge[].class);

        var graphDw = new DWGraph_DS();

        for (edge_data edge : edges) {
            graphDw.addNode(new Node(edge.getSrc(), 0, null, 0, null));
            graphDw.addNode(new Node(edge.getDest(), 0, null, 0, null));
            graphDw.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
        }

        return graphDw;
    }
}
