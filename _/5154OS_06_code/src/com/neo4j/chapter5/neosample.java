import org.neo4j.graphdb.*;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
* Example class that constructs a simple graph with
* message attributes and then prints them.
*/

public class NeoOneMinute {
	public enum MyRelationshipTypes implements RelationshipType {
		KNOWS
	}

	public static void main(String[] args) {
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("var/base");
		Transaction tx = graphDb.beginTx();
		try {
			Node node1 = graphDb.createNode();
			Node node2 = graphDb.createNode();
			Relationship someRel =	node1.createRelationshipTo(node2, MyRelationshipTypes.KNOWS);

			node1.setProperty("message", "Hello, ");
			node2.setProperty("message", "world!");
			someRel.setProperty("message", "brave Neo4j ");
			tx.success();

			System.out.print(node1.getProperty("message"));
			System.out.print(someRel.getProperty("message"));
			System.out.print(node2.getProperty("message"));
			}
		finally {
			tx.finish();
			graphDb.shutdown();
			}
	}
}
