package com.oscar.context;

import java.io.File;
import java.net.URL;

public abstract class AbstractClassLoader {
    public static File[] getFiles(String packageName){

        //Load the classLoader which loads this class.
        ClassLoader classLoader = ControllerAnnotationProcessor.class.getClassLoader();
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Change the package structure to directory structure
        String packagePath = packageName.replace('.', '/');
        URL urls = classLoader.getResource(packagePath);

        //Get all the class files in the specified URL Path.
        File folder = new File(urls.getPath());
        File[] files = folder.listFiles();
        System.out.println("Files are:");

        // Display the names of the files
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getPath());
            System.out.println(files[i].getName());
            System.out.println(files[i].getAbsoluteFile());
        }
        return folder.listFiles();



    }
}
