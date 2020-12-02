package json;

import com.google.gson.InstanceCreator;
import graph_implementation.Edge;

import java.lang.reflect.Type;

public class EdgeDataInstanceCreator implements InstanceCreator<Edge> {

    public EdgeDataInstanceCreator() {
    }

    @Override
    public Edge createInstance(Type type) {
        return new Edge(0, 0,0);
    }

}