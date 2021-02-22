package tech.iooo.boot.statemachine;

/**
 * @author 龙也
 * @date 2021/2/22 2:54 下午
 */
public enum DemoEvent {
    APPLY("提交审核"),
    AUDIT_PASS("通过"),
    AUDIT_FAIL("不通过"),
    AUDIT_ERROR("审批异常"),
    DISTRIBUTE("发起分配"),
    DISTRIBUTE_ALL_CONFIRM("分配全部确认"),
    DISTRIBUTE_PART_FAIL("分配包含不确认"),
    AUTO_VALID("到期自动生效"),
    EXPIRE("过期"),
    REBUILD_GOAL("发起目标重设"),
    UNLOCK_GOAL("解锁"),
    UNLOCK_CURRENT_GOAL("解锁当前目标");

    private final String desc;

    DemoEvent(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
