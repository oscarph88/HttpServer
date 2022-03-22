package com.oscar.context;

import com.oscar.context.annotations.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControllerAnnotationProcessor  extends AbstractClassLoader{
    private static Map<String, Class<?>> controllersPath = new HashMap<>();
    private static Map<String, Method> controllerMethods = new HashMap<>();


    public static void load(String packageName){
        try {

            File[] classes = getFiles(packageName);
            int size = classes.length;

            for (int i = 0; i < size; i++) {
                int index = classes[i].getName().indexOf(".");
                String className = classes[i].getName().substring(0, index);
                String parentFileDirectory= classes[i].getParentFile().getName();
                String classNamePath = packageName + "." + parentFileDirectory + "." + className;
                //String classNamePath = packageName + "." + className;
                Class<?> clazz = Class.forName(classNamePath);
                if(clazz.isAnnotationPresent(RestController.class)) {
                    RestController annotation = clazz.getAnnotation(RestController.class);
                    controllersPath.put(annotation.value(), clazz);
                    loadControllerMethods(annotation.value(), clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadControllerMethods(String path, Class<?> clazz) {
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(GET.class)) {
                controllerMethods.put(path + method.getAnnotation(GET.class).path(), method);
            }
            if(method.isAnnotationPresent(POST.class)) {
                controllerMethods.put(path + method.getAnnotation(POST.class).path(), method);
            }
            if(method.isAnnotationPresent(PUT.class)) {
                controllerMethods.put(path + method.getAnnotation(PUT.class).path(), method);
            }
            if(method.isAnnotationPresent(DELETE.class)) {
                controllerMethods.put(path + method.getAnnotation(DELETE.class).path(), method);
            }
        }
    }


    public static Map<String, Class<?>> getAllControllers() {

        return Collections.unmodifiableMap(controllersPath);
    }

    public static Map<String, Method> getAllMethods() {

        return Collections.unmodifiableMap(controllerMethods);
    }
}
