package com.flf.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class FieldSelectionTest {

    public static void printCursor( DBCursor cursor ) {
        try {
            while ( cursor.hasNext() ) {
                DBObject obj = cursor.next();
                System.out.println( obj );
            }
        } finally {
            cursor.close();
        }
    }


    public static void main( String[] args ) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB courseDB = client.getDB( "course" );
        DBCollection collection = courseDB.getCollection( "fieldSelectionTest" );

        collection.drop();
        Random rand = new Random();
        for ( int i = 0; i < 10; i++ ) {
            collection.insert( new BasicDBObject( "x", rand.nextInt( 2 ) )
                                         .append( "y", rand.nextInt( 100 ) )
                                         .append( "z", rand.nextInt( 1000 ) ) );
        }

        QueryBuilder builder = QueryBuilder.start( "x" ).is( 0 ).and( "y" ).greaterThan( 10 ).lessThan( 70 );

        System.out.println( "\nCount:" );
        System.out.println( collection.count( builder.get() ) );

        System.out.println( "\nFind all:" );
        printCursor( collection.find( builder.get(), new BasicDBObject( "y", true ).append( "_id", false ) ) );

    }
}
