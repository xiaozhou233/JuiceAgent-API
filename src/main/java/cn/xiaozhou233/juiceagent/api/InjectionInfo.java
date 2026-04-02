package cn.xiaozhou233.juiceagent.api;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class InjectionInfo {
    private static final int argsNum = 5;

    private String entryJarPath;
    private String entryClass;
    private String entryMethod;
    private String injectionDir;
    private String libPath;

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
                case "EntryJarPath":
                    entryJarPath = kv[1];
                    break;
                case "EntryClass":
                    entryClass = kv[1];
                    break;
                case "EntryMethod":
                    entryMethod = kv[1];
                    break;
                case "InjectionDir":
                    injectionDir = kv[1];
                    break;
                case "JuiceAgentNativePath":
                    libPath = kv[1];
                    break;
                default:
                    break;
            }
        }
    }
}
