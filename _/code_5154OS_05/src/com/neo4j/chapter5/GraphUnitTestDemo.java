/***
  This is the skeleton code for unit testing a neo4j embedded instance using the
  GraphUnit package of the GraphAware Neo4j framework

  This skeleton code is not intended to be run stand alone. It rather serves as a reference
  to use the tools in your application according to your dataset.

  Script: graphUnitTestDemo.java
  Author: Sonal Raj
  TimeStamp: 19-09-2014
***/

import com.graphaware.test.unit.GraphUnit;

public class GraphUnitTestDemo
{  
   @Test  
   public void testActor() 
    {  
     String actorSubgraph = "Match (country:Country {name: 'BRITAIN'})<-[:From]-(actor {name: 'PIERCE BROSNAN'}), (m:Movie {id: 5}), (min:Minutes {id: 35}) create (actor)-[:ActedIn]->(genre:Genre{type: 'Action'})-[:In]->(m) create (m)-[:BelongsTo]->(country)";  
     GraphUnit.assertSubgraph(getGraphDb(), actorSubgraph);  
    }  
   @Test  
   public void testReferee() 
    {  
     String refSubgraph = "Match (m:Movie {id:5}) match (c:Country {name:'BRITAIN'}) create (m)-[:Director]->(d:Director {name:'The Doctor'})-[:HomeCountry]->(c)";
     GraphUnit.assertSubgraph(getGraphDb(), refSubgraph);  
   }  
 }
