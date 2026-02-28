package cn.xiaozhou233.juiceagent.api;

import lombok.Getter;

@Getter
public class InjectionInfo {
    private static final int argsNum = 5;

    private final String entryJarPath;
    private final String entryClass;
    private final String entryMethod;
    private final String injectionDir;
    private final String libPath;

    public InjectionInfo(String[] args) {
        // -------- argument check --------
        if (args == null || args.length < argsNum) {
            assert args != null;
            throw new IllegalArgumentException(
                    String.format("Expected 5 arguments, but got %d arguments!", args.length)
            );
        }

        this.entryJarPath = args[0];
        this.entryClass = args[1];
        this.entryMethod = args[2];
        this.injectionDir = args[3];
        this.libPath = args[4];
    }
}
