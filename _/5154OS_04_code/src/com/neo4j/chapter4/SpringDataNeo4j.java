/*** This code skeleton demonstrates the use of Spring Data Neo4j in order to
	 create entities for a sample graph of soccer players and illustrates other methods of SDN.

  This skeleton code is not intended to be run stand alone. It rather serves as a reference
  to use the tools in your application according to your dataset.

  Script: SpringDataNeo4j.java
  TimeStamp: 19-09-2014
***/

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


//Creating a node entity
@NodeEntity
class Player {
    @Indexed(unique=true)
    private String player_name;

    @RelatedTo(direction = Direction.BOTH, elementClass = Player.class)
    private Set<Player> coPlayers;

    public Player() {}
    public Player(String player_name) { this.player_name = player_name; }

    private void playedWith(Player coPlayer) { coPlayers.add(coPlayer); }
}

class SpringDataNeo4j{
	public static void main(String[] args){
		Player ronaldo = new Player("Ronaldo").persist();
		Player beckham = new Player("Beckham").persist();
		Player rooney = new Player("Rooney").persist();

		beckham.playedWith(ronaldo);
		beckham.playedWith(rooney);

		// Persist creates relationships to graph database
		beckham.persist();

		for (Player coPlayer : beckham.getFriends()) {
		    System.out.println("Friend: " + coPlayer);
		}

		// The Method findAllByTraversal() is part of @NodeEntity
		for (Player coPlayer : ronaldo.findAllByTraversal(Player.class,
		        Traversal.description().evaluator(Evaluators.includingDepths(1, 2)))) {
		    System.out.println("Ronaldo's coPlayers to depth 2: " + coPlayer);
		}

		// Add <datagraph:repositories base-package="com.your.repo"/> to context config.
		interface com.example.repo.PlayerRepository extends GraphRepository<Player> {}

		@Autowired PlayerRepository repo;
		beckham = repo.findByPropertyValue("player_name", "beckham");
		long numberOfPeople = repo.count();
	}
}