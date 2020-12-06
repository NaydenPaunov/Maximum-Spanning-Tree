package utils;

import graph.Edge;
import graph.Vertex;
import graph.WeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GraphUtils {

    public static WeightedGraph maximumSpanningTree(WeightedGraph graph){
        int graphSize = graph.getAdjVertices().size();
        List<Edge> allEdges = graph.getAllEdges();
        allEdges.sort(Comparator.comparing(Edge::getWeight));

        WeightedGraph tree = new WeightedGraph();

        for (Edge edge : allEdges) {
            Vertex source = graph.getSourceVertex(edge);
            if (source != null) {
                tree.addEdge(source.getLabel(), edge.getTo().getLabel(), edge.getWeight());
                if (graphHasCycle(tree, source, null, new ArrayList<>()))
                    tree.removeEdge(source, edge.getTo());
                else if ((graphSize -1) * 2 == tree.getAllEdges().size())
                    break;
            }
        }

        return tree;
    }

    private static boolean graphHasCycle(WeightedGraph graph, Vertex checkVertex, Vertex parentVertex, List<Vertex> visitedNodes){
        if (checkVertex == null)
            checkVertex = graph.getFirstVertex();

        if (visitedNodes.contains(checkVertex)){
            return true;
        } else {
            visitedNodes.add(checkVertex);
            for (Edge edge : graph.getEdges(checkVertex)){
                Vertex vertex = edge.getTo();
                if (!vertex.equals(parentVertex))
                    if (graphHasCycle(graph, vertex, checkVertex, visitedNodes))
                        return true;
            }
            return false;
        }
    }
}
