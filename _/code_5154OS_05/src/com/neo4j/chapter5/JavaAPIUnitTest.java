/***
	This is the skeleton code for unit testing a neo4j embedded instance using the
	built-in Java API on the core neo4j package.

	This skeleton code is not intended to be run stand alone. It rather serves as a reference
  	to use the tools in your application according to your dataset.

	Script: unit_test_java_API.java
	Author: Sonal Raj
	TimeStamp: 19-09-2014
***/

public class JavaAPIUnitTest
{
	@Before
	public void prepareTestDatabase(){
	    graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
	}

	@After
	public void destroyTestDatabase(){
	    graphDb.shutdown();

	public static void test()
	{
		Node n = null;
		try ( Transaction tx = graphDb.beginTx() )
		{
		    n = graphDb.createNode();
		    n.setProperty( "title", "Developer" );
		    tx.success();
		}

		// Check for a valid node Id
		assertThat( n.getId(), is( greaterThan( -1L ) ) );

		// A node is retrieved with the Id of the node created. The id's and
		// property must be matching.
		try ( Transaction tx = graphDb.beginTx() )
		{
		    Node foundNode = graphDb.getNodeById( n.getId() );
		    assertThat( foundNode.getId(), is( n.getId() ) );
		    assertThat( (String) foundNode.getProperty( "title" ), is( "Developer" ) );
		}
	}
}