package tech.iooo.boot.statemachine;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:45 下午
 */
public interface From<E, S> {
    @NotNull
    When<S> when(@NotNull E var1);
}
