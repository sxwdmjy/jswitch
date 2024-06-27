package com.jswitch.sip;

import java.io.ObjectStreamException;
import java.io.Serializable;

public final class DialogState implements Serializable{

    private DialogState(int dialogState) {
        m_dialogState = dialogState;
        m_dialogStateArray[m_dialogState] = this;
    }


    public static DialogState getObject(int dialogState){
        if (dialogState >= 0 && dialogState < m_size) {
            return m_dialogStateArray[dialogState];
        } else {
            throw new IllegalArgumentException("Invalid dialogState value");
        }
    }

    public int getValue() {
        return m_dialogState;
    }


    private Object readResolve() throws ObjectStreamException {
        return m_dialogStateArray[m_dialogState];
    }


    public boolean equals(Object obj) {
        if (obj == this) return true;

        return (obj instanceof DialogState) && ((DialogState)obj).m_dialogState == m_dialogState;
    }

    public int hashCode() {
        return m_dialogState;
    }        
    
    

    public String toString() {
        String text = "";
        switch (m_dialogState) {
            case _EARLY:
                text = "Early Dialog";
                break;
            case _CONFIRMED:
                text = "Confirmed Dialog";
                break;
            case _COMPLETED:
                text = "Completed Dialog";
                break;    
            case _TERMINATED:
                text = "Terminated Dialog";
                break;                  
            default:
                text = "Error while printing Dialog State";
                break;
        }
        return text;
    }


    private int m_dialogState;
    private static int m_size = 4;
    private static DialogState[] m_dialogStateArray = new DialogState[m_size];
        

    public static final int _EARLY = 0;


    public final static DialogState EARLY = new DialogState(_EARLY);
    

    public static final int _CONFIRMED = 1;


    public final static DialogState CONFIRMED = new DialogState(_CONFIRMED);
    

    public static final int _COMPLETED = 2;

    public final static DialogState COMPLETED = new DialogState(_COMPLETED);


    public static final int _TERMINATED = 3;
    

    public final static DialogState TERMINATED = new DialogState(_TERMINATED);    
    

}





















