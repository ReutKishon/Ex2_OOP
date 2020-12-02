package json;

import com.google.gson.InstanceCreator;
import graph_implementation.Node;

import java.lang.reflect.Type;

public class NodeDataInstanceCreator implements InstanceCreator<Node> {

    public NodeDataInstanceCreator() {
    }

    @Override
    public Node createInstance(Type type) {
        return new Node();
    }

}