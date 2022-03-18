package com.oscar.context;

import com.oscar.context.annotations.Component;
import com.oscar.context.annotations.RestController;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext extends AbstractClassLoader{

    private static ApplicationContext instance;
    private ApplicationContext(){};
    private static Map<String, Class<?>> controllers = new HashMap<>();
    private static Map<Class <?>, Object> beans = new HashMap<>();
    private static Map<String, Method> actions = new HashMap<>();
    static { init(); }

    public static ApplicationContext getInstance(){
        if(instance == null ){
            instance = new ApplicationContext();
        }
        return instance;
    }

    public static void init(){
        /**
         * Procesar las anotaciones que tenga la aplicacion
         * Leer controladores, repositorios, inicializar
         */
        System.out.println("Calling init method");
        String packageName="com.oscar";
        loadAllBeans(packageName);
        loadControllers(packageName);
        loadRepositories();
    }

    private static void loadRepositories() {

    }

    private static void loadControllers(String packageName) {
        ControllerAnnotationProcessor.load(packageName);
    }

    private static void loadAllBeans(String packageName) {
        try {
            Class<?>[] annotations = {RestController.class, Component.class};
            File[] classes = getFiles(packageName);
            int size = classes.length;

            for (int i = 0; i < size; i++) {
                int index = classes[i].getName().indexOf(".");
                String className = classes[i].getName().substring(0, index);
                String classNamePath = packageName + "." + className;
                Class<?> clazz = Class.forName(classNamePath);
                for(Class<?> annotation: annotations){
                    if(clazz.isAnnotationPresent((Class<? extends Annotation>) annotation)) {
                        beans.put(clazz, clazz.getDeclaredConstructors()[0].newInstance());
                    }

                }
            }
            System.out.println("All beans have been loaded");
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getBean(Class <?> clazz){
        /**
         * Crear las instancias
         */

        return (T) beans.get(clazz);
    }

    public static Map<String, Class<?>> getMappingControllers(){
        return ControllerAnnotationProcessor.getAllControllers();
    }

    public static Map<String, Method> getMappingMethods(){
        return ControllerAnnotationProcessor.getAllMethods();
    }


}