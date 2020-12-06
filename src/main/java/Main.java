import gexf.GexfGenerator;
import graph.WeightedGraph;
import io.FileProcessor;
import utils.GraphUtils;

import java.io.IOException;

public class Main {
    private static final String VOL_FILE_NAME = "mtx_correl_ewm_vol.csv";
    private static final String RET_FILE_NAME = "mtx_correl_log_ret.csv";
    private static final String VOL_FILE_NAME_RESULT = "mtx_correl_ewm_vol_result.gexf";
    private static final String RET_FILE_NAME_RESULT = "mtx_correl_log_ret_result.gexf";
    private static final String MAX_SPANNING_TREE_VOL = "max_spanning_tree_vol.gexf";
    private static final String MAX_SPANNING_TREE_LOG = "max_spanning_tree_log.gexf";

    public static void main(String[] args) throws IOException {
        /* Fill graphs from csv files*/
        WeightedGraph volGraph = new WeightedGraph()
                .loadGraphFromCsvFile(VOL_FILE_NAME);

        WeightedGraph logReturnsGraph = new WeightedGraph()
                .loadGraphFromCsvFile(RET_FILE_NAME);

        /* Remove all loosely connected edges */
        logReturnsGraph.removeAllLooselyConnectedEdges(3);
        volGraph.removeAllLooselyConnectedEdges(3);

        /* Finding the Maximum Spanning tree of the sorted graph */
        WeightedGraph maxSpanningTreeVol = GraphUtils.maximumSpanningTree(volGraph);
        WeightedGraph maxSpanningTreeLog = GraphUtils.maximumSpanningTree(logReturnsGraph);

        /* Generate Gexf xml data */
        GexfGenerator generator = new GexfGenerator();
        String vol_result =  generator.generateGexfXml(logReturnsGraph.getAdjVertices());
        String ret_result = generator.generateGexfXml(volGraph.getAdjVertices());
        String maxSpanningTreeVolGexf = generator.generateGexfXml(maxSpanningTreeVol.getAdjVertices());
        String maxSpanningTreeLogGexf = generator.generateGexfXml(maxSpanningTreeLog.getAdjVertices());

        /* Write results to file */
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.createResultDirectory();
        fileProcessor.writeToFile(VOL_FILE_NAME_RESULT, vol_result);
        fileProcessor.writeToFile(RET_FILE_NAME_RESULT, ret_result);
        fileProcessor.writeToFile(MAX_SPANNING_TREE_LOG, maxSpanningTreeLogGexf);
        fileProcessor.writeToFile(MAX_SPANNING_TREE_VOL, maxSpanningTreeVolGexf);
    }
}
