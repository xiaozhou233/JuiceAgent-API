package cn.xiaozhou233.juiceagent.api;

import cn.xiaozhou233.juiceagent.api.bootstrap.BootstrapBridge;
import cn.xiaozhou233.juiceagent.api.bootstrap.LoaderBridge;

import java.io.File;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class JuiceAgentBootstrap {
    public static void start(String args_string) {
        // parse arguments
        InjectionInfo info = new InjectionInfo(args_string);

        // print arguments
        System.out.println("[JuiceAgent API] =================");
        System.out.println("[JuiceAgent API] Entry Jar Path: " + info.getEntryJarPath());
        System.out.println("[JuiceAgent API] Entry Class: " + info.getEntryClass());
        System.out.println("[JuiceAgent API] Entry Method: " + info.getEntryMethod());
        System.out.println("[JuiceAgent API] Injection Dir: " + info.getInjectionDir());
        System.out.println("[JuiceAgent API] JuiceAgent Library Path: " + info.getLibPath());
        System.out.println("[JuiceAgent API] =================");

        // Step.1 load native library
        File library = new File(info.getLibPath());
        if (!library.exists() || !library.isFile())
            throw new RuntimeException("JuiceAgent library not found at: " + info.getLibPath());
        System.load(library.getAbsolutePath());

        // Step.2 init JuiceAgent
        if (!JuiceAgent.init())
            throw new RuntimeException("JuiceAgent initialization failed!");

        // Step.3 inject entry class
        if(!JuiceAgent.addToSystemClassLoaderSearch(info.getEntryJarPath()))
            throw new RuntimeException("Failed to add entry jar to system classloader search path: " + info.getEntryJarPath());

        // Step.4 setup bootstrap bridge
        BootstrapBridge.setProvider(new LoaderBridge() {
            @Override
            public void startEntry() {
                System.out.println("[JuiceLoader] startEntry called!");
            }

            @Override
            public void log(String s) {
                System.out.println("[JuiceLoader] " + s);
            }
        });

        // Step.5 invoke entry class
        try {
            Class<?> clazz = Class.forName(info.getEntryClass());
            Method method = clazz.getMethod(info.getEntryMethod());
            method.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to invoke entry method " + info.getEntryClass() + "." + info.getEntryMethod(),
                    e
            );
        }
    }
}