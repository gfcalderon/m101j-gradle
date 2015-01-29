package com.flf.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class CreateRemoveTest {

    public static DBCollection createCollection( String name ) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB( "course" );
        DBCollection collection = courseDB.getCollection( name );

        collection.drop();

        return collection;
    }

    public static void printCollection( DBCollection collection ) {

        System.out.println( "\nCount:" );
        System.out.println( collection.count() );

        System.out.println( "\nFind all:" );
        DBCursor cursor = null;
        try {
            cursor = collection.find();
            while ( cursor.hasNext() ) {
                DBObject obj = cursor.next();
                System.out.println( obj );
            }
        } finally {
            cursor.close();
        }
    }


    public static void main( String[] args ) throws UnknownHostException {

        DBCollection collection = createCollection( "CreateRemoveTest" );

        List<String> names = Arrays.asList( "Alice", "Bobby", "Cathy", "David", "Ethan" );
        for ( String name : names ) {
            collection.insert( new BasicDBObject( "_id", name ) );
        }

        collection.update( new BasicDBObject( "_id", "Alice" ), new BasicDBObject( "age", 24 ) );
        collection.update( new BasicDBObject( "_id", "Alice" ), new BasicDBObject( "$set", new BasicDBObject( "gender", "Female" ) ) );

        collection.update( new BasicDBObject( "_id", "Frank" ), new BasicDBObject( "$set", new BasicDBObject( "gender", "Male" ) ), true, false );

        collection.update( new BasicDBObject(), new BasicDBObject( "$set", new BasicDBObject( "title", "Dr." ) ), false, true );

        printCollection( collection );

        System.out.println( "\n------------------------------------------------------------------------------------------------------------------------" );

        collection.remove( new BasicDBObject( "_id", "Alice" ) );

        printCollection( collection );

    }
}
