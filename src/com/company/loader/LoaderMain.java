package com.company.loader;

import com.company.loader.plugin.Plugin;
import com.company.loader.plugin.PluginManager;

import java.io.File;

/**
 * Created by lyan on 11.11.16.
 */
public class LoaderMain {
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager(new File("/home/lyan/IdeaProjects/tset/plugin/"));

        Plugin plugin = pluginManager.load("plug1", "com.company.loader.plugin.impl.CustomPluginImpl");
        plugin.run();
    }
}
