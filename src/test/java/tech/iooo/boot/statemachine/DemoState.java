package tech.iooo.boot.statemachine;

/**
 * @author 龙也
 * @date 2021/2/22 2:55 下午
 */
public enum DemoState {
    INIT("init", "初始化"),

    IN_PROCESS("in_process", "流程中"),

    REJECTED("rejected", "已驳回"),

    PASSED("passed", "已通过"),

    DISTRIBUTING("distributing", "分配中"),

    DISTRIBUTED("distributed", "分配完成"),

    VALID("valid", "生效中"),

    EXPIRED("expired", "已过期"),

    TERMINATED("terminated", "已终止");

    private final String key;

    private final String desc;

    DemoState(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getKey() {
        return key;
    }
}
