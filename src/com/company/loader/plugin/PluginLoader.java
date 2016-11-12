package com.company.loader.plugin;

import com.sun.org.apache.regexp.internal.RE;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * Created by lyan on 11.11.16.
 */
public class PluginLoader extends URLClassLoader{
    public PluginLoader(URL[] urls){
        super(urls);
    }

    @Override
    public Class<?> loadClass(String className){
        if (className.equals("com.company.loader.plugin.Plugin")||
                className.startsWith("java")){
            try {
                return super.loadClass(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return findClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
