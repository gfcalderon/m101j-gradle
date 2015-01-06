import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Hello World on MongoDB
 * User: Gerardo Fernández
 * Date: 06/01/2015
 */
public class HelloWorldMongoDB {

    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient( new ServerAddress( "localhost", ServerAddress.defaultPort() ) );

        DB database = client.getDB( "course" );
        DBCollection collection = database.getCollection( "hello" );

        DBObject document = collection.findOne();
        System.out.println( document );
    }

}
