package tech.iooo.boot.statemachine;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:46 下午
 */
public class DefaultStateMachine<E, S> implements StateMachine<E, S>, StateTransition<E, S> {
    protected final Map<S, Map<E, S>> status = new ConcurrentHashMap<>();
    protected final Map<E, Set<S>> events = new ConcurrentHashMap<>();

    public DefaultStateMachine() {
    }

    @Override
    @NotNull
    public Status<E, S> newStatus(@NotNull S initState) {
        return new InternalStatus<>(initState, this);
    }

    @Override
    @NotNull
    public Set<S> touchEvent(@NotNull E event) {
        Set<S> s = this.events.get(event);
        return s == null ? Collections.emptySet() : Collections.unmodifiableSet(s);
    }

    @Override
    @NotNull
    public Transition<E, S> transition() {
        return new ExternalTransition<>(this);
    }
}

