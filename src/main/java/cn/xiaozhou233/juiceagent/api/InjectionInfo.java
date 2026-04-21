package cn.xiaozhou233.juiceagent.api;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class InjectionInfo {
    private static final int argsNum = 5;

    private String Version;
    private String JuiceAgentAPIJarPath;
    private String JuiceAgentNativeLibraryPath;
    private String RuntimeDir;

    public InjectionInfo(String args_string) {
        if (args_string == null || args_string.isEmpty()) {
            throw new IllegalArgumentException("args_string cannot be null or empty");
        }

        String[] args = args_string.split(";");
        for (String arg : args) {
            String[] kv = arg.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }
            switch (kv[0]) {
                case "Version":
                    Version = kv[1];
                    break;
                case "JuiceAgentAPIJarPath":
                    JuiceAgentAPIJarPath = kv[1];
                    break;
                case "JuiceAgentNativeLibraryPath":
                    JuiceAgentNativeLibraryPath = kv[1];
                    break;
                case "RuntimeDir":
                    RuntimeDir = kv[1];
                    break;
                default:
                    break;
            }
        }
    }
}
