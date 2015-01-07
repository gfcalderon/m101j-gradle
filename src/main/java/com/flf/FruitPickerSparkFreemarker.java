package com.flf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Gerardo Fernández
 * Date: 07/01/2015
 */
public class FruitPickerSparkFreemarker {

    public static void main( String[] args ) {

        Configuration configuration = new Configuration( Configuration.VERSION_2_3_21 );
        configuration.setClassForTemplateLoading( FruitPickerSparkFreemarker.class, "/" );

        Spark.get( "/", ( request, response ) -> {

            try {

                Map<String, Object> fruitMap = new HashMap<>();
                fruitMap.put( "fruits", Arrays.asList( "apple", "orange", "banana", "peach" ) );

                Template helloTemplate = configuration.getTemplate( "fruitPicker.ftl" );
                StringWriter writer = new StringWriter();
                helloTemplate.process( fruitMap, writer );
                return writer;

            } catch ( Exception e ) {
                Spark.halt( 500 );
                return null;
            }

        } );

        Spark.post( "/favoriteFruit", ( request, response ) -> {

            String fruit = request.queryParams( "fruit" );
            if ( fruit == null ) {
                return "Why don't you pick one?";
            } else {
                return "Your favorite fruit is " + fruit;
            }

        } );
    }

}
