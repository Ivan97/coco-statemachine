package tech.iooo.boot.statemachine;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:44 下午
 */
public interface StateMachine<E, S> {

    static <DE, DS> StateTransition<DE, DS> buildDefaultStateMachine(Class<DE> eventClass, Class<DS> stateClass) {
        return new DefaultStateMachine();
    }

    @NotNull
    Status<E, S> newStatus(@NotNull S var1);

    @NotNull
    Set<S> touchEvent(@NotNull E var1);
}
