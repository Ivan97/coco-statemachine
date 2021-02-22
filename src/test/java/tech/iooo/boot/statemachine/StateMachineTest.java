package tech.iooo.boot.statemachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static tech.iooo.boot.statemachine.DemoEvent.APPLY;
import static tech.iooo.boot.statemachine.DemoEvent.AUDIT_ERROR;
import static tech.iooo.boot.statemachine.DemoEvent.AUDIT_FAIL;
import static tech.iooo.boot.statemachine.DemoEvent.AUDIT_PASS;
import static tech.iooo.boot.statemachine.DemoEvent.AUTO_VALID;
import static tech.iooo.boot.statemachine.DemoEvent.DISTRIBUTE;
import static tech.iooo.boot.statemachine.DemoEvent.DISTRIBUTE_ALL_CONFIRM;
import static tech.iooo.boot.statemachine.DemoEvent.DISTRIBUTE_PART_FAIL;
import static tech.iooo.boot.statemachine.DemoEvent.EXPIRE;
import static tech.iooo.boot.statemachine.DemoEvent.REBUILD_GOAL;
import static tech.iooo.boot.statemachine.DemoEvent.UNLOCK_CURRENT_GOAL;
import static tech.iooo.boot.statemachine.DemoEvent.UNLOCK_GOAL;
import static tech.iooo.boot.statemachine.DemoState.DISTRIBUTED;
import static tech.iooo.boot.statemachine.DemoState.DISTRIBUTING;
import static tech.iooo.boot.statemachine.DemoState.EXPIRED;
import static tech.iooo.boot.statemachine.DemoState.INIT;
import static tech.iooo.boot.statemachine.DemoState.IN_PROCESS;
import static tech.iooo.boot.statemachine.DemoState.PASSED;
import static tech.iooo.boot.statemachine.DemoState.REJECTED;
import static tech.iooo.boot.statemachine.DemoState.VALID;

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

        stateTransition.transition().from(IN_PROCESS).when(AUDIT_PASS).to(PASSED);
        stateTransition.transition().from(IN_PROCESS).when(AUDIT_FAIL).to(REJECTED);
        stateTransition.transition().from(IN_PROCESS).when(AUDIT_ERROR).to(INIT);

        stateTransition.transition().from(PASSED).when(DISTRIBUTE).to(DISTRIBUTING);
        stateTransition.transition().from(DISTRIBUTING).when(DISTRIBUTE_ALL_CONFIRM).to(DISTRIBUTED);
        stateTransition.transition().from(DISTRIBUTING).when(DISTRIBUTE_PART_FAIL).to(PASSED);

        //人的状态机，能从通过到生效
        stateTransition.transition().from(PASSED).when(AUTO_VALID).to(VALID);
        //截止时间过去之后自动生效
        stateTransition.transition().from(INIT).when(AUTO_VALID).to(VALID);
        stateTransition.transition().from(IN_PROCESS).when(AUTO_VALID).to(PASSED);

        //组织的状态机，能从分配完成到生效
        stateTransition.transition().from(DISTRIBUTED).when(AUTO_VALID).to(VALID);

        stateTransition.transition().from(VALID).when(EXPIRE).to(EXPIRED);

        //发起目标重设
        stateTransition.transition().from(IN_PROCESS).when(REBUILD_GOAL).to(INIT);
        stateTransition.transition().from(REJECTED).when(REBUILD_GOAL).to(INIT);
        stateTransition.transition().from(PASSED).when(REBUILD_GOAL).to(INIT);
        stateTransition.transition().from(DISTRIBUTING).when(REBUILD_GOAL).to(INIT);
        stateTransition.transition().from(DISTRIBUTED).when(REBUILD_GOAL).to(INIT);
        stateTransition.transition().from(VALID).when(REBUILD_GOAL).to(INIT);
        //解锁
        stateTransition.transition().from(IN_PROCESS).when(UNLOCK_GOAL).to(INIT);
        stateTransition.transition().from(REJECTED).when(UNLOCK_GOAL).to(INIT);
        stateTransition.transition().from(PASSED).when(UNLOCK_GOAL).to(INIT);
        stateTransition.transition().from(DISTRIBUTING).when(UNLOCK_GOAL).to(INIT);
        stateTransition.transition().from(DISTRIBUTED).when(UNLOCK_GOAL).to(INIT);
        stateTransition.transition().from(VALID).when(UNLOCK_GOAL).to(INIT);

        stateTransition.transition().from(REJECTED).when(UNLOCK_CURRENT_GOAL).to(PASSED);
        stateTransition.transition().from(DISTRIBUTING).when(UNLOCK_CURRENT_GOAL).to(PASSED);
        stateTransition.transition().from(DISTRIBUTED).when(UNLOCK_CURRENT_GOAL).to(PASSED);
        stateTransition.transition().from(VALID).when(UNLOCK_CURRENT_GOAL).to(PASSED);
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