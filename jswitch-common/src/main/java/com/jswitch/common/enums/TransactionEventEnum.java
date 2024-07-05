package com.jswitch.common.enums;

/**
 * @author danmo
 * @date 2024-07-03 10:58
 **/
public enum TransactionEventEnum {

    START(0, "Start"),
    //1XX临时应答
    TEMPORARY(1, "Temporary"),
    //300-699失败应答
    FAILURE(2, "Failure"),
    //2xx 成功应答
    SUCCESS(3, "Success"),

    //ACK
    ACK(4, "Ack"),

    NO_RESPONSE(5, "NoResponse"),


    ;

    private final int event;
    private final String description;

    TransactionEventEnum(int event, String description) {
        this.event = event;
        this.description = description;
    }

}
