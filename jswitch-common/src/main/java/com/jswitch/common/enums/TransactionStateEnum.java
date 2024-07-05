package com.jswitch.common.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * 事务状态
 * @author danmo
 * @date 2024-06-13 17:08
 **/
@Getter
public enum TransactionStateEnum implements Serializable {

    INITIAL(-1, "Initial"),
    CALLING(0, "Calling"),
    TRYING(1, "Trying"),
    PROCEEDING(2, "Proceeding"),
    COMPLETED(3, "Completed"),
    CONFIRMED(4, "Confirmed"),
    TERMINATED(5, "Terminated");

    TransactionStateEnum(int state, String description) {
        this.state = state;
        this.description = description;
    }

    private final int state;
    private final String description;

    public static TransactionStateEnum getState(int state) {
        for (TransactionStateEnum transactionState : TransactionStateEnum.values()) {
            if (transactionState.state == state) {
                return transactionState;
            }
        }
        return null;
    }
}
