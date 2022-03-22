package com.oscar.context;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClassLoader {
    static List<File> filesList= new ArrayList<File>();

    public static File[] getFiles(String packageName){

        //Load the classLoader which loads this class.
        ClassLoader classLoader = ControllerAnnotationProcessor.class.getClassLoader();
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Change the package structure to directory structure
        String packagePath = packageName.replace('.', '/');
        URL urls = classLoader.getResource(packagePath);

        //Get all the class files in the specified URL Path.
        File folder = new File(urls.getPath());

        //Start fixing
        filesList.clear();
        listFilesForFolder(folder);
        System.out.println("List size "+filesList.size());
        File[] files = new File[filesList.size()];
        filesList.toArray(files);
        //End

        return files;
    }

    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                System.out.println(fileEntry.getPath());
                System.out.println(fileEntry.getParentFile().getName());
                if(!fileEntry.getName().equals(".DS_Store")) {
                    filesList.add(fileEntry);
                }
            }
        }
    }

}
