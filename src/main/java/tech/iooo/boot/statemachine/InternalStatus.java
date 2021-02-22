package tech.iooo.boot.statemachine;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

/**
 * @author 龙也
 * @date 2021/2/22 2:47 下午
 */
public class InternalStatus<E, S> implements Status<E, S> {
    private final DefaultStateMachine<E, S> stateMachine;
    private S currentState;
    private S previousState;
    private E lastFireEvent;

    public InternalStatus(@NotNull S currentState, @NotNull DefaultStateMachine<E, S> stateMachine) {
        this.stateMachine = stateMachine;
        this.currentState = currentState;
    }

    @Override
    public S getCurrentState() {
        return this.currentState;
    }

    @Override
    public S getPreviousState() {
        return this.previousState;
    }

    @Override
    public E getLastFireEvent() {
        return this.lastFireEvent;
    }

    @Override
    @NotNull
    public SMResult fire(E event) {
        if (this.currentState == null) {
            return SMResult.NO_FROM_STATUS;
        } else {
            Map<E, S> arc = this.stateMachine.status.get(this.currentState);
            if (arc == null) {
                return SMResult.NO_EVENT;
            } else {
                S toState = arc.get(event);
                if (toState == null) {
                    return SMResult.NO_TO_STATUS;
                } else {
                    this.previousState = this.currentState;
                    this.currentState = toState;
                    this.lastFireEvent = event;
                    return SMResult.SUCCESS;
                }
            }
        }
    }

    @Override
    public boolean canFire(E event) {
        Map<E, S> arc = this.stateMachine.status.get(this.currentState);
        return arc != null && arc.containsKey(event);
    }

    @Override
    public Set<E> canFireEvents() {
        Map<E, S> arc = this.stateMachine.status.get(this.currentState);
        return arc == null ? Collections.emptySet() : Collections.unmodifiableSet(arc.keySet());
    }

    @Override
    public String toString() {
        return "InternalStatus{currentState=" + this.currentState
            + ", previousState=" + this.previousState
            + ", lastFireEvent=" + this.lastFireEvent
            + '}';
    }
}

