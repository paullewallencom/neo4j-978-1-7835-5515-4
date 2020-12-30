/*** This code skeleton demonstrates the use of transaction library
     in the Neo4j Java API. Refer chapter for the REST API related implementations

  This skeleton code is not intended to be run stand alone. It rather serves as a reference
  to use the tools in your application according to your dataset.

  Script: TransactionDemo.java
  TimeStamp: 19-09-2014
***/

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

class TransactionDemo
{
    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    }

    public void transactionDemo() {
        
        GraphDatabaseService graphDatabase;
        Node node1;
        Node node2;
        Relationship rel;

        graphDatabase = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        registerShutdownHook( graphDatabase );
        
        Transaction txn = graph.beginTx();
        try {
            node1 = graphDatabase.createNode();
            node1.setProperty( "name", "David Tennant" );
            node2 = graphDatabase.createNode();
            node2.setProperty( "name", "Matt Smith" );

            rel = node1.createRelationshipTo( node2, RelTypes.KNOWS );
            rel.setProperty( "name", "precedes " );

            node1.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            node1.delete();
            node2.delete();

            txn.success();
        } catch (Exception e) {
            txn.failure();
        } finally {
            txn.finish();
        }
    }

}