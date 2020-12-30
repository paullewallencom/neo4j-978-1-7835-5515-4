import org.neo4j.graphdb.Node;
import org.neo4j.kernel.Traversal;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphdb.RelationshipExpander;
/**
 * Finding shortest path (least weighted) in a graph
 */
public class DijkstraPath
{
    private final GraphServiceHelper graph;
    //Included in the code folder
    private static final String WEIGHT = "weight";
    private static final CostEvaluator<Double> evalCost;
    private static final PathFinder<WeightedPath> djktraFindPath;
    private static final RelationshipExpander relExpndr;
    
    static
    {
        // configure the path finder
        evalCost = CommonEvaluators.doubleCostEvaluator( WEIGHT );
        relExpndr = Traversal.expanderForTypes(GraphServiceHelper.MyDijkstraTypes.REL, Direction.BOTH );
        djktraFindPath = GraphAlgoFactory.dijkstra( relExpndr, evalCost );
    }

    public DijkstraPath()
    {
        graph = new GraphServiceHelper( "path_to_database" );
    }

    private void constructGraph()
    {
        graph.createRelationship( "n1", "n2", WEIGHT, 10d );
        graph.createRelationship( "n2", "n5", WEIGHT, 10d );
        graph.createRelationship( "n1", "n3", WEIGHT, 5d );
        graph.createRelationship( "n3", "n4", WEIGHT, 10d );
        graph.createRelationship( "n4", "n5", WEIGHT, 5d );
    }

    /**
     * Find the path.
     */
    private void executeDijkstraFindPath()
    {
        Node begin = graph.getNode( "n1" );
        Node endd = graph.getNode( "n5" );
        WeightedPath path = djktraFindPath.findSinglePath( begin, endd );
        for ( Node node : path.nodes() )
        {
            System.out.println( node.getProperty( GraphServiceHelper.NAME ) );
        }
    }

    /**
     * Shutdown the graphdb.
     */
    private void stop()
    {
        graph.shutdown();
    }

    /**
     * Execute the example.
     */
    public static void main( final String[] args )
    {
        DijkstraPath obj = new DijkstraPath();
        obj.constructGraph();
        obj.executeDijkstraFindPath();
        obj.stop();
    }
}
