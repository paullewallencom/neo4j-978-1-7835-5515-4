/*** This code skeleton demonstrates the use of the various graph algorithms 
     including A*, Dijkstra's, etc to be used with the Java API of Neo4j.

  This skeleton code is not intended to be run stand alone. It rather serves as a reference
  to use the tools in your application according to your dataset.

  Script: GraphAlgorithms.java
  TimeStamp: 19-09-2014
***/

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TargetDirectory;

class GraphAlgorithms
{
    public static void main(String args[])
    {
        //Create a sample graph
        Node cityA = createNode( "city", "London", "x", 0d, "y", 0d );
        Node cityB = createNode( "city", "New York", "x", 7d, "y", 0d );
        Node cityC = createNode( "city", "Bangalore", "x", 2d, "y", 1d );
        Relationship distAB = createRelationship( cityA, cityC, "distance",20d );
        Relationship distBC = createRelationship( cityC, cityB, "distance",30d );
        Relationship distAC = createRelationship( cityA, cityB, "distance",100d );

        EstimateEvaluator<Double> estimateEvaluator = new EstimateEvaluator<Double>()
        {
            @Override
            public Double getCost( final Node node, final Node goal )
            {
                double costx = (Double) node.getProperty( "x" ) - (Double) goal.getProperty( "x" );
                double costy = (Double) node.getProperty( "y" ) - (Double) goal.getProperty( "y" );
                double answer = Math.sqrt( Math.pow( costx, 2 ) + Math.pow( costy, 2 ) );
                return answer;
            }
        };

        //Use the A* algorithm
        PathFinder<WeightedPath> astarFinder = GraphAlgoFactory.aStar(PathExpanders.allTypesAndDirections(),
            CommonEvaluators.doubleCostEvaluator( "distance" ), estimateEvaluator );
        WeightedPath astarPath = astarFinder.findSinglePath( cityA, cityB );


        //Using the Dijkstra's Algorithm
        PathFinder<WeightedPath> dijkstraFinder = GraphAlgoFactory.dijkstra(
            PathExpanders.forTypeAndDirection( ExampleTypes.REL_TYPE, Direction.BOTH ), "distance" );

        WeightedPath shortestPath = dijkstraFinder.findSinglePath( cityA, cityC );
        //print the weight of this path
        System.out.println(shortestPath.weight());


        //Using the find all paths method
        PathFinder<Path> allPathFinder = GraphAlgoFactory.shortestPath(
            PathExpanders.forTypeAndDirection( ExampleTypes.REL_TYPE, Direction.OUTGOING ), 15 );
        Iterable<Path> all_paths = allPathFinder.findAllPaths( cityA, cityC );

    }
}