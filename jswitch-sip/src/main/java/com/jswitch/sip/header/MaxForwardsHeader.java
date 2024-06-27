package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.TooManyHopsException;

/**
 * @author danmo
 * @date 2024-06-18 15:16
 **/
public interface MaxForwardsHeader extends Header {

    public void decrementMaxForwards() throws TooManyHopsException;

    public int getMaxForwards();

    public void setMaxForwards(int maxForwards) throws InvalidArgumentException;


    public boolean equals(Object obj);


    public final static String NAME = "Max-Forwards";
}
