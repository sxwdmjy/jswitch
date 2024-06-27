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
/****************************************************************************
 * Product of NIST/ITL Advanced Networking Technologies Division (ANTD).    *
 ****************************************************************************/
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.NameValue;

import java.util.Locale;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public final class AcceptLanguage extends ParametersHeader implements AcceptLanguageHeader {

    private static final long serialVersionUID = -4473982069737324919L;
    protected String languageRange;

    public AcceptLanguage() {
        super(NAME);
    }

    @Override
    public StringBuilder encodeBody(StringBuilder encoding) {
        if (languageRange != null) {
            encoding.append(languageRange);
        }
        if (!parameters.isEmpty()) {
            encoding.append(SEMICOLON).append(parameters.encode());
        }
        return encoding;
    }


    public String getLanguageRange() {
        return languageRange;
    }


    public float getQValue() {
        if (!hasParameter("q"))
            return -1;
        return (Float) parameters.getValue("q");
    }


    public boolean hasQValue() {
        return hasParameter("q");
    }


    public void removeQValue() {
        removeParameter("q");
    }


    public void setLanguageRange(String languageRange) {
        this.languageRange = languageRange.trim();
    }


    public void setQValue(float q) throws InvalidArgumentException {
        if (q < 0.0 || q > 1.0)
            throw new InvalidArgumentException("qvalue out of range!");
        if (q == -1)
            this.removeParameter("q");
        else
            this.setParameter(new NameValue("q", q));
    }


    public Locale getAcceptLanguage() {
        if (this.languageRange == null)
            return null;
        else {
            int dash = languageRange.indexOf('-');
            if (dash >= 0) {
                return new Locale(languageRange.substring(0, dash), languageRange.substring(dash + 1));
            } else return new Locale(this.languageRange);
        }
    }

    public void setAcceptLanguage(Locale language) {
        // JvB: need to take sub-tag into account
        if ("".equals(language.getCountry())) {
            this.languageRange = language.getLanguage();
        } else {
            this.languageRange = language.getLanguage() + '-' + language.getCountry();
        }
    }

}
