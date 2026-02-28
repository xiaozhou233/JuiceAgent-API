package cn.xiaozhou233.juiceagent.api;

@SuppressWarnings("unused")
public class JuiceAgent {

    // ===== Environment =====
    public static native boolean init();

    // ===== ClassPath Injection =====
    public static native boolean addToBootstrapClassLoaderSearch(String jarPath);
    public static native boolean addToSystemClassLoaderSearch(String jarPath);
    public static native boolean addToClassLoader(String jarPath, ClassLoader loader);

    // ===== Define Class =====
    public native static Class<?> defineClass(ClassLoader loader, byte[] bytes);

    // ===== Redefine Class =====
    public static native boolean redefineClass(Class<?> clazz, byte[] classBytes, int length);
    public static native boolean redefineClassByName(String className, byte[] classBytes, int length);

    // ===== Retransform Class =====
    public static native boolean retransformClass(Class<?> clazz, byte[] classBytes, int length);
    public static native boolean retransformClassByName(String className, byte[] classBytes, int length);

    // ===== Query Classes =====
    public static native Class<?>[] getLoadedClasses();
    public static native Class<?> getClassByName(String className);

    // ===== Bytecode Access =====
    public static native byte[] getClassBytes(Class<?> clazz);
    public static native byte[] getClassBytesByName(String className);
}