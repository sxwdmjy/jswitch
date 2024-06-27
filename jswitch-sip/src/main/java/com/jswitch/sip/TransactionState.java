package com.jswitch.sip;

import lombok.Getter;

import java.io.Serializable;

/**
 * 事务状态
 * @author danmo
 * @date 2024-06-13 17:08
 **/
@Getter
public enum TransactionState implements Serializable {

    CALLING(0, "Calling"),
    TRYING(1, "Trying"),
    PROCEEDING(2, "Proceeding"),
    COMPLETED(3, "Completed"),
    CONFIRMED(4, "Confirmed"),
    TERMINATED(5, "Terminated");

    TransactionState(int state, String description) {
        this.state = state;
        this.description = description;
    }

    private final int state;
    private final String description;

    public static TransactionState getState(int state) {
        for (TransactionState transactionState : TransactionState.values()) {
            if (transactionState.state == state) {
                return transactionState;
            }
        }
        return null;
    }
}
