package com.flf.week2;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * User: Gerardo Fernández
 * Date: 25/01/2015
 */
public class InsertTest {

    public static void main( String[] args ) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB courseDB = client.getDB( "course" );
        DBCollection collection = courseDB.getCollection( "insertTest" );

        DBObject doc = new BasicDBObject( "_id", new ObjectId() ).append( "x", 1 );

        collection.insert( doc );

    }

}
