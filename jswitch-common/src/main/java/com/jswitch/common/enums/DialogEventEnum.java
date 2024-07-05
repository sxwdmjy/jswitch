package com.jswitch.common.enums;

public enum DialogEventEnum {

    EARLY(1, "Early"),
    CONFIRMED(2, "Confirmed"),
    COMPLETED(3, "Completed"),
    TERMINATED(4, "Terminated");

    private int event;
    private String description;

    DialogEventEnum(int event, String description) {
        this.event = event;
        this.description = description;
    }

}
