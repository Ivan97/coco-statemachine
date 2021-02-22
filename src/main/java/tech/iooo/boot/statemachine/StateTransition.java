package tech.iooo.boot.statemachine;

/**
 * @author 龙也
 * @date 2021/2/22 2:45 下午
 */
public interface StateTransition<E, S> extends StateMachine<E, S> {
    Transition<E, S> transition();
}