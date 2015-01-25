package com.flf.week1;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Gerardo Fernández
 * Date: 07/01/2015
 */
public class HelloWorldFreemarker {

    public static void main( String[] args ) {

        Configuration configuration = new Configuration( Configuration.VERSION_2_3_21 );
        configuration.setClassForTemplateLoading( HelloWorldFreemarker.class, "/" );

        try {
            Template helloTemplate = configuration.getTemplate( "hello.ftl" );
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap<String, Object>();
            helloMap.put( "name", "Freemarker" );

            helloTemplate.process( helloMap, writer );

            System.out.println( writer );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
