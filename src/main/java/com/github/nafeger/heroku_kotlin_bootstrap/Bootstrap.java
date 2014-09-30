package com.github.nafeger.heroku_kotlin_bootstrap;
/**
 * This class is used to boostrap the kotlin application. It may not be necessary, but I haven't figured out a way
 * to get kotlin to make a main method java can call directly.
 */
public class Bootstrap {
    public static void main(String...args) {
        // I think a better design for this would be to make MainKotlin's main method
        // exist in its 'object' so there could be just one of them.

//        MainKotlin.object$.main(args);
        new MainKotlin().main(args);
    }
}
