package cn.xiaozhou233.juiceagent.api;

import cn.xiaozhou233.juiceagent.api.bootstrap.BootstrapBridge;
import cn.xiaozhou233.juiceagent.api.bootstrap.LoaderBridge;

import java.io.File;

@SuppressWarnings("unused")
public class JuiceAgentBootstrap {
    public static void start(String args_string) {
        // parse arguments
        InjectionInfo info = new InjectionInfo(args_string);

        // print arguments
        System.out.println("[JuiceAgent API] =================");
        System.out.println("[JuiceAgent API] Config Version : " + info.getVersion());
        System.out.println("[JuiceAgent API] JuiceAgentAPIJarPath : " + info.getJuiceAgentAPIJarPath());
        System.out.println("[JuiceAgent API] JuiceAgentNativeLibraryPath : " + info.getJuiceAgentNativeLibraryPath());
        System.out.println("[JuiceAgent API] RuntimeDir : " + info.getRuntimeDir());
        System.out.println("[JuiceAgent API] =================");

        // Step.1 load native library
        File library = new File(info.getJuiceAgentNativeLibraryPath());
        if (!library.exists() || !library.isFile())
            throw new RuntimeException("JuiceAgent library not found at: " + info.getJuiceAgentNativeLibraryPath());
        System.load(library.getAbsolutePath());

        // Step.2 init JuiceAgent
        if (!JuiceAgent.init(info.getRuntimeDir()))
            throw new RuntimeException("JuiceAgent initialization failed!");

        // Step.3 setup bootstrap bridge
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

    }
}