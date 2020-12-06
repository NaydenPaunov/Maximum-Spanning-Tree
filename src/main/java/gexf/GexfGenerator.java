package gexf;

import graph.Edge;
import graph.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GexfGenerator
{
    public String generateGexfXml(Map<Vertex, List<Edge>> graph)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">");
       // sb.append("<gexf xmlns=\"http://www.gexf.net/1.3\" version=\"1.3\" xmlns:viz=\"http://www.gexf.net/1.3/viz\"");
       // sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.3http://www.gexf.net/1.3/gexf.xsd\">");
        sb.append("<graph defaultedgetype=\"undirected\" mode=\"static\"><nodes>");

        Map<String, Integer> labelIdPair = new HashMap<>(graph.size());
        int count = 0;

        for (Vertex vertex : graph.keySet())
        {
            sb.append(String.format("<node id=\"%d\" label=\"%s\"></node>",count, vertex.getLabel()));
            labelIdPair.put(vertex.getLabel(), count++);
        }
        sb.append("</nodes><edges>");

        int edgeCount = 0;
        for (Map.Entry<Vertex, List<Edge>> entry : graph.entrySet())
        {
            int sourceId = labelIdPair.get(entry.getKey().getLabel());
            for (Edge edge : entry.getValue())
            {
                int targetId = labelIdPair.get(edge.getTo().getLabel());

                sb.append(String.format("<edge id=\"%d\" source=\"%d\" target=\"%d\" weight=\"%.10f\"></edge>",
                        edgeCount, sourceId, targetId, edge.getWeight()));
                edgeCount++;
            }
        }
        sb.append("</edges></graph></gexf>");
        return sb.toString();
    }
}
