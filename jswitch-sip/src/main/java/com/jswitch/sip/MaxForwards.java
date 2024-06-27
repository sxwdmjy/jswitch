package com.jswitch.sip;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.TooManyHopsException;
import com.jswitch.sip.header.MaxForwardsHeader;
import com.jswitch.sip.header.SIPHeader;
import lombok.Getter;

/**
 * @author danmo
 * @date 2024-06-18 15:16
 **/
@Getter
public class MaxForwards extends SIPHeader implements MaxForwardsHeader {

    private static final long serialVersionUID = -1L;

    protected int maxForwards;


    public MaxForwards() {
        super(NAME);
    }

    public MaxForwards(int m) throws InvalidArgumentException {
        super(NAME);
        this.setMaxForwards(m);
    }


    public void setMaxForwards(int maxForwards)
            throws InvalidArgumentException {
        if (maxForwards < 0 || maxForwards > 255)
            throw new InvalidArgumentException(
                    "bad max forwards value " + maxForwards);
        this.maxForwards = maxForwards;
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(maxForwards);
    }

    public boolean hasReachedZero() {
        return maxForwards == 0;
    }


    public void decrementMaxForwards() throws TooManyHopsException {
        if (maxForwards > 0)
            maxForwards--;
        else throw new TooManyHopsException("has already reached 0!");
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof MaxForwardsHeader) {
            final MaxForwardsHeader o = (MaxForwardsHeader) other;
            return this.getMaxForwards() == o.getMaxForwards();
        }
        return false;
    }
}
