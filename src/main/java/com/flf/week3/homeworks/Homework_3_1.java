package com.flf.week3.homeworks;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * User: Gerardo Fernández
 * Date: 01/04/2015
 */
public class Homework_3_1 {

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
        DB courseDB = client.getDB( "school" );
        DBCollection collection = courseDB.getCollection( "students" );

        DBCursor cursor = collection.find()
                                    .sort( new BasicDBObject( "_id", 1 ) );

        System.out.println( "\nCount:" );
        System.out.println( cursor.count() );

        System.out.println( "\nFind all:" );
        //printCursor( cursor );

        while ( cursor.hasNext() ) {

            DBObject doc = cursor.next();
            BasicDBList scores = (BasicDBList) doc.get( "scores" );
            Double lowestScore = (double) 0;
            BasicDBObject dbObject = null;

            for ( Object object : scores ) {

                BasicDBObject score = (BasicDBObject) object;

                if ( score.get( "type" ).equals( "homework" ) ) {

                    Double latestScore = (Double) score.get( "score" );

                    if ( lowestScore.compareTo( (double) 0 ) == 0 ) {
                        lowestScore = latestScore;
                        dbObject = score;
                    } else if ( lowestScore.compareTo( latestScore ) > 0 ) {
                        lowestScore = latestScore;
                        dbObject = score;
                    }

                }
            }

            System.out.println( "object to be removed : " + dbObject + ":" + scores.remove( dbObject ) );
            collection.update( new BasicDBObject( "_id", doc.get( "_id" )), doc, true, false );

        }

        cursor.close();

    }
}
