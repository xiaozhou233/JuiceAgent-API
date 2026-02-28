package cn.xiaozhou233.juiceagent.api.bootstrap;

import lombok.Getter;
import lombok.Setter;

public final class BootstrapBridge {
    @Getter
    @Setter
    private static volatile LoaderBridge provider;

    private BootstrapBridge() { }
}
