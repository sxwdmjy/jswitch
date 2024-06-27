package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

@Getter
public class SubscriptionState extends ParametersHeader implements SubscriptionStateHeader {


    private static final long serialVersionUID = -6673833053927258745L;
    protected int expires;
    protected int retryAfter;
    protected String reasonCode;
    protected String state;

    public SubscriptionState() {
        super(SIPHeaderNames.SUBSCRIPTION_STATE);
        expires = -1;
        retryAfter = -1;
    }


    public void setExpires(int expires) throws InvalidArgumentException {
        if (expires < 0)
            throw new InvalidArgumentException("SIPException, SubscriptionState, setExpires(), the expires parameter is  < 0");
        this.expires = expires;
    }


    public void setRetryAfter(int retryAfter) throws InvalidArgumentException {
        if (retryAfter <= 0)
            throw new InvalidArgumentException("SIP Exception, SubscriptionState, setRetryAfter(), the retryAfter parameter is <=0");
        this.retryAfter = retryAfter;
    }

    public void setReasonCode(String reasonCode) throws ParseException {
        if (reasonCode == null)
            throw new NullPointerException("SIP Exception, SubscriptionState, setReasonCode(), the reasonCode parameter is null");
        this.reasonCode = reasonCode;
    }


    public void setState(String state) throws ParseException {
        if (state == null)
            throw new NullPointerException("SIP Exception, SubscriptionState, setState(), the state parameter is null");
        this.state = state;
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (state != null)
            buffer.append(state);
        if (reasonCode != null)
            buffer.append(";reason=").append(reasonCode);
        if (expires != -1)
            buffer.append(";expires=").append(expires);
        if (retryAfter != -1)
            buffer.append(";retry-after=").append(retryAfter);

        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }
        return buffer;
    }
}

