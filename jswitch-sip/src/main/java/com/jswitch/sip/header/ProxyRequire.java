/*
* Conditions Of Use
*
* This software was developed by employees of the National Institute of
* Standards and Technology (NIST), an agency of the Federal Government.
* Pursuant to title 15 Untied States Code Section 105, works of NIST
* employees are not subject to copyright protection in the United States
* and are considered to be in the public domain.  As a result, a formal
* license is not needed to use the software.
*
* This software is provided by NIST as a service and is expressly
* provided "AS IS."  NIST MAKES NO WARRANTY OF ANY KIND, EXPRESS, IMPLIED
* OR STATUTORY, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTY OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT
* AND DATA ACCURACY.  NIST does not warrant or make any representations
* regarding the use of the software or the results thereof, including but
* not limited to the correctness, accuracy, reliability or usefulness of
* the software.
*
* Permission to use this software is contingent upon your acceptance
* of the terms of this agreement
*
* .
*
*/
/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD).        *
*******************************************************************************/
package com.jswitch.sip.header;


import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.PROXY_REQUIRE;

public class ProxyRequire extends SIPHeader implements ProxyRequireHeader {


    private static final long serialVersionUID = -3269274234851067893L;

    protected String optionTag;


    public ProxyRequire() {
        super(PROXY_REQUIRE);
    }


    public ProxyRequire(String s) {
        super(PROXY_REQUIRE);
        optionTag = s;
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(optionTag);
    }


    public void setOptionTag(String optionTag) throws ParseException {
        if (optionTag == null)
            throw new NullPointerException("SIP Exception, ProxyRequire, setOptionTag(), the optionTag parameter is null");
        this.optionTag = optionTag;
    }


    public String getOptionTag() {
        return optionTag;
    }
}
