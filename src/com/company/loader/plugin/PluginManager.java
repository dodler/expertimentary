package com.company.loader.plugin;

import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * Created by lyan on 11.11.16.
 */
public class PluginManager {

    private String filePluginDir;

    public PluginManager(File rootPluginDir){
        filePluginDir = rootPluginDir.getAbsolutePath();
    }

    public Plugin load(String name, String pluginClass){
        PluginLoader loader;
        try {
            String url = "file://" + this.filePluginDir + "/" + name + "/";
            System.out.println(url);
            loader = new PluginLoader(new URL[]{new URL(url)});
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(pluginClass);

        Class<?> clazz = loader.loadClass(pluginClass);
        try {
            return (Plugin)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to load plugin");



    }
}
