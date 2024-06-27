package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.NameValue;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SubscriptionState;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SubscriptionStateParser extends HeaderParser {


    public SubscriptionStateParser(String subscriptionState) {
        super(subscriptionState);
    }


    protected SubscriptionStateParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("SubscriptionStateParser.parse");

        SubscriptionState subscriptionState = new SubscriptionState();
        try {
            headerName(TokenTypes.SUBSCRIPTION_STATE);

            subscriptionState.setHeaderName(SIPHeaderNames.SUBSCRIPTION_STATE);

            // State:
            lexer.match(TokenTypes.ID);
            Token token = lexer.getNextToken();
            subscriptionState.setState(token.getTokenValue());

            while (lexer.lookAhead(0) == ';') {
                this.lexer.match(';');
                this.lexer.SPorHT();

                NameValue nv = this.nameValue('=');
                if (nv.getName().equalsIgnoreCase("reason")) {
                    subscriptionState.setReasonCode(nv.getValue());
                } else if (nv.getName().equalsIgnoreCase("expires")) {
                    try {
                        int expires = Integer.parseInt(nv.getValue());
                        subscriptionState.setExpires(expires);
                    } catch (NumberFormatException | InvalidArgumentException ex) {
                        throw createParseException(ex.getMessage());
                    }
                } else if (nv.getName().equalsIgnoreCase("retry-after")) {
                    try {
                        int retryAfter = Integer.parseInt(nv.getValue());
                        subscriptionState.setRetryAfter(retryAfter);
                    } catch (NumberFormatException | InvalidArgumentException ex) {
                        throw createParseException(ex.getMessage());
                    }
                } else {
                    subscriptionState.setParameter(nv);
                }
                this.lexer.SPorHT();
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("SubscriptionStateParser.parse");
        }

        return subscriptionState;
    }

}
