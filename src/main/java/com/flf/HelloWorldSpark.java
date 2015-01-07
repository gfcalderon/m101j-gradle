package com.flf;

import spark.Spark;

/**
 * User: Gerardo Fernández
 * Date: 07/01/2015
 */
public class HelloWorldSpark {

    public static void main( String[] args ) {

        Spark.get( "/", ( request, response ) -> "Hello world from Spark" );
    }

}
