
package com.jswitch.sip.header;

import com.jswitch.sip.GenericURI;
import com.jswitch.sip.URI;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public final class AlertInfo extends ParametersHeader implements AlertInfoHeader {


    private static final long serialVersionUID = 4159657362051508719L;

    protected GenericURI uri;

    protected String string;


    public AlertInfo() {
        super(NAME);
    }


    @Override
    public StringBuilder encodeBody(StringBuilder encoding) {
//        StringBuilder encoding = new StringBuilder();
        if (uri != null) {
            encoding.append(LESS_THAN).append(uri.encode()).append(GREATER_THAN);
        } else if (string != null) {
            encoding.append(string);
        }
        if (!parameters.isEmpty()) {
            encoding.append(SEMICOLON).append(parameters.encode());
        }
        return encoding;
    }


    public void setAlertInfo(URI uri) {
        this.uri = (GenericURI) uri;
    }


    public void setAlertInfo(String string) {
        this.string = string;
    }


    public URI getAlertInfo() {
        URI alertInfoUri = null;

        if (this.uri != null) {
            alertInfoUri = (URI) this.uri;
        } else {
            try {
                alertInfoUri = (URI) new GenericURI(string);
            } catch (ParseException e) {
                ;  // Eat the exception.
            }
        }

        return alertInfoUri;
    }

    public Object clone() {
        AlertInfo retval = (AlertInfo) super.clone();
        if (this.uri != null) {
            retval.uri = (GenericURI) this.uri.clone();
        } else if (this.string != null) {
            retval.string = this.string;
        }
        return retval;
    }
}
