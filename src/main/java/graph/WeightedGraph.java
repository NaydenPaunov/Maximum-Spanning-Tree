package graph;

import io.CsvReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeightedGraph {
    private Map<Vertex, List<Edge>> adjVertices;

    public WeightedGraph(){
        this.adjVertices = new HashMap<>();
    }

    public Map<Vertex, List<Edge>> getAdjVertices() {
        if(this.adjVertices == null)
            this.adjVertices = new HashMap<>();
        return this.adjVertices;
    }

    public void addVertex(String label) {
        this.adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void removeVertex(String label) {
        Vertex vertex = new Vertex(label);
        this.adjVertices.values().stream().forEach(e -> e.remove(vertex));
        this.adjVertices.remove(new Vertex(label));
    }

    public void addEdge(String from, String to, double weight) {
        if (from.equals(to))
            return;

        Vertex vertexFrom = new Vertex(from);
        Vertex vertexTo = new Vertex(to);

        if (findEdgeOfVertex(vertexFrom, vertexTo) == null)
            addEdge(vertexFrom, new Edge(vertexTo, weight));

        if (findEdgeOfVertex(vertexTo, vertexFrom) == null)
            addEdge(vertexTo, new Edge(vertexFrom, weight));
    }

    public void addEdge(Vertex parent, Edge edge){
        if (!this.adjVertices.containsKey(parent))
            this.adjVertices.putIfAbsent(parent, new ArrayList<>());

        this.adjVertices.get(parent).add(edge);
    }

    public void removeEdge(Vertex from, Vertex to){
        Edge remEdge = findEdgeOfVertex(from, to);
        this.adjVertices.get(from).remove(remEdge);

        remEdge = findEdgeOfVertex(to, from);
        this.adjVertices.get(to).remove(remEdge);
    }

    public List<Edge> getAllEdges() {
        return this.adjVertices.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    public WeightedGraph loadGraphFromCsvFile(String fileName) {
        CsvReader reader = new CsvReader(fileName);
        List<String[]> fileContent = reader.readCsvFile();
        return fillGraphFromCsvContent(fileContent);
    }

    public void removeAllLooselyConnectedEdges(int top) {
        for (List<Edge> edges : adjVertices.values()) {
            Collections.sort(edges);
            removeAllEdgesExceptFirstCount(edges, top);
        }
    }

    public List<Edge> getEdges(Vertex vertex) {
        return this.adjVertices.get(vertex);
    }

    public Vertex getFirstVertex() {
        if(this.adjVertices == null)
            return null;
        return this.adjVertices.entrySet().iterator().next().getKey();
    }

    public Vertex getSourceVertex(Edge edge){
        for (HashMap.Entry<Vertex,List<Edge>> entry : this.adjVertices.entrySet()) {
            if (entry.getValue().contains(edge))
                return entry.getKey();
        }

        return null;
    }

    private Edge findEdgeOfVertex(Vertex from, Vertex to){
        List<Edge> edges = this.adjVertices.get(from);

        if(edges != null)
            for (Edge edge : edges) {
                if (to.equals(edge.getTo()))
                    return edge;
            }
        return null;
    }

    private void removeAllEdgesExceptFirstCount(List<Edge> edges, int top) {
        int count = 0;
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            count++;
            iterator.next();
            if(count <= top)
                continue;
            iterator.remove();
        }
    }

    private WeightedGraph fillGraphFromCsvContent(List<String[]> content) {
        String[] labels = content.get(0);

        for (int row = 1; row < content.size(); row++) {
            String[] currentRow = content.get(row);
            String labelFrom = currentRow[0];
            addVertex(labelFrom);

            for (int col = 1; col < currentRow.length; col++) {
                if(col > row )
                    continue;

                String currentCol = currentRow[col];
                String labelTo = labels[col];
                addEdge(labelFrom, labelTo, Double.parseDouble(currentCol));
            }
        }
        return this;
    }
}
