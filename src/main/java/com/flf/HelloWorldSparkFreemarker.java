package com.flf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Gerardo Fernández
 * Date: 07/01/2015
 */
public class HelloWorldSparkFreemarker {

    public static void main( String[] args ) {

        Configuration configuration = new Configuration( Configuration.VERSION_2_3_21 );
        configuration.setClassForTemplateLoading( HelloWorldSparkFreemarker.class, "/" );

        Spark.get( "/", ( request, response ) -> {

            StringWriter writer = new StringWriter();

            try {
                Template helloTemplate = configuration.getTemplate( "hello.ftl" );
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put( "name", "Freemarker" );

                helloTemplate.process( helloMap, writer );
            } catch ( Exception e ) {
                Spark.halt( 500 );
                e.printStackTrace();
            }

            return writer;
        } );
    }

}
