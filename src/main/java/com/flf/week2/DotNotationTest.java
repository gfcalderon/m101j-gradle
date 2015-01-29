package com.flf.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class DotNotationTest {

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
        DBCollection collection = courseDB.getCollection( "dotNotationTest" );

        collection.drop();
        Random rand = new Random();
        for ( int i = 0; i < 10; i++ ) {
            collection.insert( new BasicDBObject( "_id", i )
                                         .append( "start", new BasicDBObject( "x", rand.nextInt( 90 ) + 10 )
                                                                     .append( "y", rand.nextInt( 90 ) + 10 ) )
                                         .append( "end", new BasicDBObject( "x", rand.nextInt( 90 ) + 10 )
                                                 .append( "y", rand.nextInt( 90 ) + 10 ) ) );
        }

        QueryBuilder builder = QueryBuilder.start("start.x").greaterThan( 50 );

        System.out.println( "\nCount:" );
        System.out.println( collection.count( builder.get() ) );

        System.out.println( "\nFind all:" );
        printCursor( collection.find( builder.get(), new BasicDBObject( "start.x", true ).append( "start.y", true ).append( "_id", false ) ) );

    }
}
