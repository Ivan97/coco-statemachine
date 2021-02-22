package tech.iooo.boot.statemachine;

/**
 * @author 龙也
 * @date 2021/2/22 2:46 下午
 */
public enum SMResult {
    SUCCESS,
    NO_FROM_STATUS,
    NO_EVENT,
    NO_TO_STATUS,
    NO_COMPLETE_FLOW;

    private SMResult() {
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this);
    }
}

