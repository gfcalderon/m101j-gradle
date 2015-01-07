package com.flf;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * User: Gerardo Fernández
 * Date: 07/01/2015
 */
public class HelloWorldSparkFreemarkerMongoDB {

    public static void main( String[] args ) throws UnknownHostException {

        Configuration configuration = new Configuration( Configuration.VERSION_2_3_21 );
        configuration.setClassForTemplateLoading( HelloWorldSparkFreemarkerMongoDB.class, "/" );

        MongoClient client = new MongoClient( new ServerAddress( "localhost", ServerAddress.defaultPort() ) );

        DB database = client.getDB( "course" );
        DBCollection collection = database.getCollection( "hello" );

        Spark.get( "/", ( request, response ) -> {

            StringWriter writer = new StringWriter();

            try {
                Template helloTemplate = configuration.getTemplate( "hello.ftl" );
                DBObject document = collection.findOne();

                helloTemplate.process( document, writer );
            } catch ( Exception e ) {
                Spark.halt( 500 );
                e.printStackTrace();
            }

            return writer;
        } );
    }

}
