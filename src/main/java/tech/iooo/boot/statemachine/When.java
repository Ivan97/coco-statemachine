package tech.iooo.boot.statemachine;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:46 下午
 */
public interface When<S> {
    void to(@NotNull S var1);
}

