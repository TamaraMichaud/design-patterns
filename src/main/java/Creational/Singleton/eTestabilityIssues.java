package Creational.Singleton;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class eTestabilityIssues {
    public static void main(String[] args) {
        new TestClass().trueUnitTest();
    }
}

@SuppressWarnings("WeakerAccess")
// if we apply unit testing to RecordFinder. we should be able to call it without being stuck to the "LiveDatabase" class...
class RecordFinder {
    public int getTotalPopulation(List<String> cityNames){
        int result = 0 ;
        for(String cityName : cityNames) {
            result += LiveDatabase.getInstance().getPopulationCount(cityName);
        }
        return result;
    }
}

@SuppressWarnings("WeakerAccess")
class ConfigurableRecordFinder { // << now our singleton obj is injected as a dependency so all good

    private InterfaceDB database;

    public ConfigurableRecordFinder(InterfaceDB database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> cityNames){
        int result = 0 ;
        for(String cityName : cityNames) {
            result += database.getPopulationCount(cityName);
        }
        return result;
    }
}
@SuppressWarnings({"WeakerAccess", "unused"})
class TestClass {
    @Test
    public void SingletonPopulationTest(){ // not a unit test...

        List<String> dummyCities = List.of("TEST_CITY1", "TEST_CITY3"); // << we would need to know that these cities certainly
        // exist in the live database... which we cant. but now they do exist in the test db
        RecordFinder rf = new RecordFinder();
        int totalPopulation = rf.getTotalPopulation(dummyCities);
        assertEquals(59382 + 23457, totalPopulation);

        // ^^ for this to work, dummyCities, and the expected value in AssertEquals() would have to be
        // valid values from the LiveDatabase - over which we have zero control... this stinks!
    }
    @Test
    public void trueUnitTest(){ // IS a unit test...

        List<String> dummyCities = List.of("TEST_CITY1", "TEST_CITY3"); // << we would need to know that these cities certainly
        // exist in the live database... which we cant. but now they do exist in the test db

        // so we must make RecordFinder() not be bound to any specific data source.
        //
        // this can be done by introducing the DB-interface class below so we can implement it in our TESTDb too
        // and a configurable RecordFinder which then takes this db as a dependency
        TESTDatabase dummyRecords = TESTDatabase.getInstance();
        ConfigurableRecordFinder crf = new ConfigurableRecordFinder(dummyRecords);

        int totalPopulation1 = crf.getTotalPopulation(dummyCities);
        assertEquals(1 + 3, totalPopulation1);

    }

}

interface InterfaceDB {
    int getPopulationCount(String name);
}
@SuppressWarnings("WeakerAccess")
class TESTDatabase implements InterfaceDB {

    private static final TESTDatabase INSTANCE = new TESTDatabase();
    public static TESTDatabase getInstance(){ return INSTANCE;}

    private Dictionary<String, Integer> dummyData = new Hashtable<>();

    private TESTDatabase() {  // << THIS DATA IS SAFE FOR USE IN TESTING....
        System.out.println("Initialiszing TEST DB");
        this.dummyData.put("TEST_CITY1", 1);
        this.dummyData.put("TEST_CITY2", 2);
        this.dummyData.put("TEST_CITY3", 3);

    }

    @Override
    public int getPopulationCount(String name) {
        return this.dummyData.get(name);
    }
}

@SuppressWarnings("WeakerAccess")
class LiveDatabase implements InterfaceDB {

    private static final LiveDatabase INSTANCE = new LiveDatabase();
    public static LiveDatabase getInstance(){ return INSTANCE;}

    public int getPopulationCount(String cityName){
        return capitals.get(cityName);
    }

    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instanceCount = 0;
    public static int getCount(){return instanceCount;}

    private LiveDatabase(){
        instanceCount++;
        System.out.println("Initializing LIVE db");
        try {
            String wtf = "../../src/main/java/Creational.SingletonPattern/";
            File fp = new File(LiveDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath() + wtf);
            // ^^ wtf is this; how does a path become a file...?
            Path theFileButCalledPathWtf = Paths.get(fp.getPath() + "/e_capitals.txt");
            List<String> lines = Files.readAllLines(theFileButCalledPathWtf);
            for(int i = 0; i < lines.size(); i++) {
                capitals.put(lines.get(i), Integer.parseInt(lines.get(++i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("count: " + getCount());
    }
}

