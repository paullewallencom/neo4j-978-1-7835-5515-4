package com.neo4j.chapter1;

import org.neo4j.graphDBase.Node;
import org.neo4j.graphDBase.Direction;
import org.neo4j.graphDBase.Relationship;
import org.neo4j.graphDBase.Transaction;
import org.neo4j.graphDBase.RelationshipType;
import org.neo4j.graphDBase.factory.GraphDatabaseFactory;

public class HelloNeo {
   //change the path according to your system and OS
   private static final String PATH_TO_DB = "path_to_your neo4j_installation";

   String response;
   GraphDatabaseService graphDBase;
   Node node_one;
   Node node_two;
   Relationship relation;

   private static enum RelationTypes implements RelationshipType { HATES }
   
   public static void main( final String[] args )
   {
       HelloNeo neoObject = new App();
       neoObject.createGraphDb();
       neoObject.removeGraph();
       neoObject.shutDownDbServer();   
   }
   
   void createGraphDb()
   {
       graphDBase = new GraphDatabaseFactory().newEmbeddedDatabase( PATH_TO_DB );

       Transaction tx = graphDBase.beginTx();
       try
       {
           node_one = graphDBase.createNode();
           node_one.setProperty( "name", "Bill Gates, Microsoft" );
           node_two = graphDBase.createNode();
           node_two.setProperty( "name", "Larry Page, Google" );

           relation = node_one.createRelationshipTo( node_two, RelationTypes.HATES );
           relation.setProperty( "relationship-type", "hates" );
           
           response = ( node_one.getProperty( "name" ).toString() )
                      + " " + ( relation.getProperty( "relationship-type" ).toString() )
                      + " " + ( node_two.getProperty( "name" ).toString() );
           System.out.println(response);

           tx.success();
       }
       finally
       {
           tx.finish();
       }
   }
   
   void removeGraph()
   {
       Transaction tx = graphDBase.beginTx();
       try
       {
           node_one.getSingleRelationship( RelationTypes.HATES, Direction.OUTGOING ).delete();
           System.out.println("Nodes are being removed . . .");
           node_one.delete();
           node_two.delete();
           tx.success();
       }
       finally
       {
           tx.finish();
       }
   }
   
   void shutDownDbServer()
   {
       graphDBase.shutdown();
       System.out.println("graphDBase is shutting down."); 
   }   
}
