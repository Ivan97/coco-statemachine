package tech.iooo.boot.statemachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:48 下午
 */
public class ExternalTransition<E, S> implements From<E, S>, When<S>, Transition<E, S> {
    private final DefaultStateMachine<E, S> stateMachine;
    private S fromState;
    private E event;

    public ExternalTransition(@NotNull DefaultStateMachine<E, S> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    @NotNull
    public From<E, S> from(@NotNull S fromState) {
        this.fromState = fromState;
        return this;
    }

    @Override
    @NotNull
    public When<S> when(@NotNull E event) {
        this.event = event;
        return this;
    }

    @Override
    public void to(@NotNull S toState) {
        Map<E, S> arc = this.stateMachine.status.computeIfAbsent(this.fromState,
            (k) -> new ConcurrentHashMap<>(3));
        arc.put(this.event, toState);
        Set<S> states = this.stateMachine.events.computeIfAbsent(this.event,
            (k) -> Collections.synchronizedSet(new HashSet<>()));
        states.add(this.fromState);
    }
}
