package cn.xiaozhou233.juiceagent.api.modules;

import cn.xiaozhou233.juiceagent.api.JuiceAgent;
import cn.xiaozhou233.juiceagent.api.utils.Deserializer;

import java.io.File;
import java.lang.reflect.Method;

public class JarLoader {

    public static void loadJar(String params) {
        System.out.println("[JuiceAgent JarLoader] Got params: " + params);

        Deserializer deserializer = new Deserializer(params);

        String jarPath = deserializer.get("JarPath", null);
        String entryClass = deserializer.get("EntryClass", null);
        String entryMethod = deserializer.get("EntryMethod", null);
        String injectionDir = deserializer.get("InjectionDir", null);

        System.out.println("[JuiceAgent JarLoader] JarPath: " + jarPath);
        System.out.println("[JuiceAgent JarLoader] EntryClass: " + entryClass);
        System.out.println("[JuiceAgent JarLoader] EntryMethod: " + entryMethod);
        System.out.println("[JuiceAgent JarLoader] InjectionDir: " + injectionDir);

        if (injectionDir != null) {
            File directory = new File(injectionDir);
            File[] files = directory.listFiles();

            if (directory.exists() && directory.isDirectory() && files != null) {
                System.out.println("[JuiceAgent JarLoader] Scanning injection directory...");

                int success = 0;
                int failed = 0;

                for (File file : files) {
                    try {
                        if (!file.isFile()) {
                            continue;
                        }

                        String fileName = file.getName().toLowerCase();

                        if (!fileName.endsWith(".jar")) {
                            continue;
                        }

                        String filePath = file.getAbsolutePath();

                        System.out.println("[JuiceAgent JarLoader] Found injection jar: " + filePath);

                        if (JuiceAgent.addToSystemClassLoaderSearch(filePath)) {
                            success++;
                            System.out.println("[JuiceAgent JarLoader] Loaded injection jar: " + filePath);
                        } else {
                            failed++;
                            System.out.println("[JuiceAgent JarLoader] Failed to load injection jar: " + filePath);
                        }

                    } catch (Exception e) {
                        failed++;
                        System.out.println("[JuiceAgent JarLoader] Error loading injection jar: " + file.getAbsolutePath());
                        e.printStackTrace();
                    }
                }

                System.out.println("[JuiceAgent JarLoader] Injection finished. Success=" + success + ", Failed=" + failed);

            } else {
                System.out.println("[JuiceAgent JarLoader] Injection directory not found or empty: " + injectionDir);
            }
        } else {
            System.out.println("[JuiceAgent JarLoader] InjectionDir is null, skip injection loading.");
        }

        System.out.println("[JuiceAgent JarLoader] Loading entry jar: " + jarPath);

        if (!JuiceAgent.addToSystemClassLoaderSearch(jarPath)) {
            throw new RuntimeException("Failed to load jar: " + jarPath);
        }

        System.out.println("[JuiceAgent JarLoader] Entry jar loaded successfully.");

        try {
            System.out.println("[JuiceAgent JarLoader] Resolving class: " + entryClass);

            Class<?> targetClass = Class.forName(entryClass);

            System.out.println("[JuiceAgent JarLoader] Resolving method: " + entryMethod);

            Method targetMethod = targetClass.getMethod(entryMethod);

            System.out.println("[JuiceAgent JarLoader] Invoking method: " + entryClass + "." + entryMethod);

            targetMethod.invoke(null);

            System.out.println("[JuiceAgent JarLoader] Entry method invoked successfully.");

        } catch (Exception e) {
            System.out.println("[JuiceAgent JarLoader] Failed to invoke entry method.");
            throw new RuntimeException(
                    "Failed to invoke: " + entryClass + "." + entryMethod,
                    e
            );
        }
    }
}