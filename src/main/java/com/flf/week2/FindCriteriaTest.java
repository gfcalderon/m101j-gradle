package com.flf.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class FindCriteriaTest {

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
        DBCollection collection = courseDB.getCollection( "findCriteriaTest" );

        collection.drop();
        for ( int i = 0; i < 10; i++ ) {
            collection.insert( new BasicDBObject( "x", new Random().nextInt( 2 ) )
                                         .append( "y", new Random().nextInt( 100 ) ) );
        }
        QueryBuilder builder = QueryBuilder.start( "x" ).is( 0 ).and( "y" ).greaterThan( 10 ).lessThan( 90 );
        DBObject query = new BasicDBObject( "x", 0 )
                                   .append( "y", new BasicDBObject ( "$gt", 10 ).append( "$lt", 90 ) );

        System.out.println( "\nCount:" );
        System.out.println( collection.count( builder.get() ) );
        System.out.println( collection.count( query ) );

        System.out.println( "\nFind all:" );
        printCursor( collection.find( builder.get() ) );
        System.out.println( "------------------------------------------------------------------------------" );
        printCursor( collection.find( query ) );

    }
}
