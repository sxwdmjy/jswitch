package com.jswitch.server.listener;

import com.jswitch.common.enums.TransactionStateEnum;

/**
 * @author danmo
 * @date 2024-07-05 16:37
 **/
public interface TransactionListener {

    void changeState(TransactionStateEnum state, String transactionId);
}
