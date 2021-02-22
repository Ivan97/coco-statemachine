package tech.iooo.boot.statemachine;

import java.util.Set;

/**
 * @author 龙也
 * @date 2021/2/22 2:46 下午
 */
public interface Status<E, S> {
    S getCurrentState();

    S getPreviousState();

    E getLastFireEvent();

    SMResult fire(E var1);

    boolean canFire(E var1);

    Set<E> canFireEvents();
}

