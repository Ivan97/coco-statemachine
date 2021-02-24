# coco-statemachine

---

```java
package tech.iooo.boot.statemachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static tech.iooo.boot.statemachine.DemoEvent.*;
import static tech.iooo.boot.statemachine.DemoState.*;

/**
 * @author 龙也
 * @date 2021/2/22 2:54 下午
 */
@RunWith(JUnit4.class)
public class StateMachineTest {

    private StateTransition<DemoEvent, DemoState> stateTransition;

    @Before
    public void before() {

        stateTransition = StateMachine.buildDefaultStateMachine(DemoEvent.class, DemoState.class);

        stateTransition.transition().from(INIT).when(APPLY).to(IN_PROCESS);
        stateTransition.transition().from(REJECTED).when(APPLY).to(IN_PROCESS);
    }

    @Test
    public void test() {
        Status<DemoEvent, DemoState> status = stateTransition.newStatus(INIT);
        if (status.canFire(APPLY)) {
            SMResult fire = status.fire(APPLY);
            if (fire.isSuccess()) {
                System.out.println("fired");
                System.out.println("current state " + status.getCurrentState());
                assertEquals(IN_PROCESS, status.getCurrentState());
            }
        }
    }
}
```