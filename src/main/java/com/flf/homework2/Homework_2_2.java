package com.flf.homework2;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class Homework_2_2 {

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
        DB courseDB = client.getDB( "students" );
        DBCollection collection = courseDB.getCollection( "grades" );

        QueryBuilder builder = QueryBuilder.start( "type" ).is( "homework" );
        DBCursor cursor = collection.find( builder.get() ).sort( new BasicDBObject( "student_id", 1 ).append( "score", 1 ) );

        System.out.println( "\nCount:" );
        System.out.println( collection.count( builder.get() ) );

        System.out.println( "\nFind all:" );
        //printCursor( cursor );

        int last_student_id = -1;
        while ( cursor.hasNext() ) {

            BasicDBObject doc = (BasicDBObject) cursor.next();

            if ( doc.getInt( "student_id" ) != last_student_id ) {
                BasicDBObject deleteDoc = (BasicDBObject) collection.findOne( new BasicDBObject( "_id", doc.getObjectId( "_id" ) ) );
                System.out.println( " Removing score " + deleteDoc.getString( "score" ) + " for student " + deleteDoc.getInt( "student_id" ) );
                collection.remove( deleteDoc );
            }

            last_student_id = doc.getInt( "student_id" );

        }

    }
}
