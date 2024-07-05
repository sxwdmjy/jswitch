package com.jswitch.server.machine;

import com.jswitch.common.enums.TransactionEventEnum;
import com.jswitch.common.enums.TransactionStateEnum;
import com.jswitch.server.msg.SipMessageEvent;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author danmo
 * @date 2024-07-03 15:17
 **/
@Component
public class ServerTransactionAction implements Action<TransactionStateEnum, TransactionEventEnum> {

    @Override
    public void execute(StateContext<TransactionStateEnum, TransactionEventEnum> context) {
        //SipMessageEvent messageEvent = (SipMessageEvent)context.getMessageHeader("msg");
        //MessageHeaders headers = context.getMessage().getHeaders();
        Object o = context.getExtendedState().getVariables().get("msgId");
        System.out.println("TransactionAction msgId:" + o);
        switch (context.getSource().getId()){
            case INITIAL:
                break;
            case TRYING:
                break;
            case PROCEEDING:
                break;
            case COMPLETED:
                break;
            case CONFIRMED:
                break;
            case TERMINATED:
                break;
            default:
                break;
        }

        System.out.println("TransactionAction status:" + context.getSource().getId());
    }
}
