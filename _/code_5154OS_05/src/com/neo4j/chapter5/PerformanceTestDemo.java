/***
  This is the skeleton code for performance testing a neo4j embedded instance using the
  Neo4j GraphAware framework's performane testing libraries for documentation. It runs tests 
  for each scenario based on 3 different configurations.

  This skeleton code is not intended to be run stand alone. It rather serves as a reference
  to use the tools in your application according to your dataset.

  Script: PerformanceTestDemo.java
  Based on: GraphAware PerformanceTest
  TimeStamp: 19-09-2014
***/

import com.graphaware.test.performance.*; //Use this or specific imports below
import com.graphaware.tx.executor.batch.NoInputBatchTransactionExecutor;
import com.graphaware.tx.executor.NullItem;
import com.graphaware.test.performance.PerformanceTest;
import com.graphaware.tx.executor.batch.UnitOfWork;
import com.graphaware.test.performance.PerformanceTestSuite;
import org.neo4j.graphdb.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Map;

public class PerformanceTestDemo implements PerformanceTest {
    enum Scenario {
        FIRST_SCENARIO,
        OTHER_SCENARIO
    }
     /**{@inheritDoc}*/
    @Override
    public String longName() {return "Test Long Name";}
    /**{@inheritDoc}*/
    @Override
    public String shortName() {return "test-short-name";}

    /**{@inheritDoc}*/
    @Override
    public List<Parameter> parameters() {
        List<Parameter> result = new LinkedList<>();
        result.add(new CacheParameter("cache")); //no cache, low-level cache, high-level cache
        result.add(new EnumParameter("scenario", Scenario.class));
        return result;
    }

    /**{@inheritDoc}*/
    @Override
    public int dryRuns(Map<String, Object> params) {
        return ((CacheConfiguration) params.get("cache")).needsWarmup() ? 10000 : 100;
    }
    /**{@inheritDoc}*/
    @Override
    public int measuredRuns() {
        return 100;
    }
    /**{@inheritDoc}*/
    @Override
    public Map<String, String> databaseParameters(Map<String, Object> params) {
        return ((CacheConfiguration) params.get("cache")).addToConfig(Collections.<String, String>emptyMap());
    }
    /**{@inheritDoc}*/
    @Override
    public void prepareDatabase(GraphDatabaseService database, final Map<String, Object> params) {
        //create 100 nodes in batches of 100
        new NoInputBatchTransactionExecutor(database, 100, 100, new UnitOfWork<NullItem>() {
            @Override
            public void execute(GraphDatabaseService database, NullItem input, int batchNumber, int stepNumber) {
                database.createNode();
            }
        }).execute();
    }
    /**{@inheritDoc}*/
    @Override
    public RebuildDatabase rebuildDatabase() {
        return RebuildDatabase.AFTER_PARAM_CHANGE;
    }
    /**{@inheritDoc}*/
    @Override
    public long run(GraphDatabaseService database, Map<String, Object> params) {
        Scenario scenario = (Scenario) params.get("scenario");
        switch (scenario) {
            case FIRST_SCENARIO:
                //run test for scenario 1
                return 20; //the time it took in microseconds
            case OTHER_SCENARIO:
                //run test for scenario 2
                return 20; //the time it took in microseconds
            default:
                throw new IllegalStateException("Unknown scenario");
        }
    }
    /**{@inheritDoc}*/
    @Override
    public boolean rebuildDatabase(Map<String, Object> params) {
        throw new UnsupportedOperationException("never needed, database rebuilt after every param change");
    }
}
/**
 * You would change the run method implementation to do some real work. Then add this test to a test suite and run it
 */
public class RunningDemoTest extends PerformanceTestSuite {
    /**{@inheritDoc}*/
    @Override
    protected PerformanceTest[] getPerfTests() {
        return new PerformanceTest[]{new PerformanceTestDemo()};
    }
}
