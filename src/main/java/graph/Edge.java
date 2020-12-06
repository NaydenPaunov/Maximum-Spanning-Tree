package graph;

public class Edge implements Comparable<Edge>{
    private Vertex to;
    private Double weight;

    public Edge(Vertex to, double weight){
        this.to = to;
        this.weight = weight;
    }

    public Vertex getTo() {
        return to;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge edge) {
        if(this.weight == null || edge.getWeight() == null) {
            return 0;
        }
        Double current = Math.abs(this.weight);
        Double other = Math.abs(edge.getWeight());
        return other.compareTo(current);
    }
}