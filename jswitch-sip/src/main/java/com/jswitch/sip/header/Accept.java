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


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.ParameterNames;

import static com.jswitch.common.constant.Separators.STAR;

public final class Accept extends ParametersHeader implements AcceptHeader {


    private static final long serialVersionUID = -7866187924308658151L;

    protected MediaRange mediaRange;


    public Accept() {
        super(NAME);
    }


    public boolean allowsAllContentTypes() {
        if (mediaRange == null)
            return false;
        else
            return mediaRange.type.compareTo(STAR) == 0;
    }


    public boolean allowsAllContentSubTypes() {
        if (mediaRange == null) {
            return false;
        } else
            return mediaRange.getSubtype().compareTo(STAR) == 0;
    }


    protected String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (mediaRange != null)
            mediaRange.encode(buffer);
        if (parameters != null && !parameters.isEmpty()) {
            buffer.append(';');
            parameters.encode(buffer);
        }
        return buffer;
    }


    public MediaRange getMediaRange() {
        return mediaRange;
    }


    public String getContentType() {
        if (mediaRange == null)
            return null;
        else
            return mediaRange.getType();
    }

    public String getContentSubType() {
        if (mediaRange == null)
            return null;
        else
            return mediaRange.getSubtype();
    }


    public float getQValue() {
        return getParameterAsFloat(ParameterNames.Q);
    }


    public boolean hasQValue() {
        return super.hasParameter(ParameterNames.Q);

    }


    public void removeQValue() {
        super.removeParameter(ParameterNames.Q);
    }


    public void setContentSubType(String subtype) {
        if (mediaRange == null)
            mediaRange = new MediaRange();
        mediaRange.setSubtype(subtype);
    }


    public void setContentType(String type) {
        if (mediaRange == null)
            mediaRange = new MediaRange();
        mediaRange.setType(type);
    }


    public void setQValue(float qValue) throws InvalidArgumentException {
        if (qValue == -1)
            super.removeParameter(ParameterNames.Q);
        super.setParameter(ParameterNames.Q, qValue);

    }


    public void setMediaRange(MediaRange m) {
        mediaRange = m;
    }

    public Object clone() {
        Accept retval = (Accept) super.clone();
        if (this.mediaRange != null)
            retval.mediaRange = (MediaRange) this.mediaRange.clone();
        return retval;
    }

}
