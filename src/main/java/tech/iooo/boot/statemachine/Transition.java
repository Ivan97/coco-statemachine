package tech.iooo.boot.statemachine;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:45 下午
 */

public interface Transition<E, S> {
    @NotNull
    From<E, S> from(@NotNull S var1);
}
