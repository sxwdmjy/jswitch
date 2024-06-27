
package com.jswitch.sip.header;


import com.jswitch.common.constant.Separators;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class SIPDate implements Cloneable, Serializable {

    private static final long serialVersionUID = 8544101899928346909L;
    public static final String GMT = "GMT";
    public static final String MON = "Mon";
    public static final String TUE = "Tue";
    public static final String WED = "Wed";
    public static final String THU = "Thu";
    public static final String FRI = "Fri";
    public static final String SAT = "Sat";
    public static final String SUN = "Sun";
    public static final String JAN = "Jan";
    public static final String FEB = "Feb";
    public static final String MAR = "Mar";
    public static final String APR = "Apr";
    public static final String MAY = "May";
    public static final String JUN = "Jun";
    public static final String JUL = "Jul";
    public static final String AUG = "Aug";
    public static final String SEP = "Sep";
    public static final String OCT = "Oct";
    public static final String NOV = "Nov";
    public static final String DEC = "Dec";

    /** sipWkDay member
     */
    protected String sipWkDay;

    /** sipMonth member
    */
    protected String sipMonth;

    /** wkday member
    */
    protected int wkday;

    /** day member
    */
    protected int day;

    /** month member
    */
    protected int month;

    /** year member
    */
    protected int year;

    /** hour member
    */
    protected int hour;

    /** minute member
    */
    protected int minute;

    /** second member
    */
    protected int second;

    /** javaCal member
    */
    private Calendar javaCal;

    /** equality check.
     *
     *@return true if the two date fields are equals
     */
    public boolean equals(Object that){
        if (that.getClass() != this.getClass())return false;
        SIPDate other = (SIPDate)that;
        return this.wkday == other.wkday &&
        this.day == other.day &&
        this.month == other.month &&
        this.year == other.year &&
        this.hour == other.hour &&
        this.minute == other.minute &&
        this.second == other.second;
    }

    /**
     * Initializer, sets all the fields to invalid values.
     */
    public SIPDate() {
        wkday = -1;
        day = -1;
        month = -1;
        year = -1;
        hour = -1;
        minute = -1;
        second = -1;
        javaCal = null;
    }

    /**
     * Construct a SIP date from the time offset given in miliseconds
     * @param timeMillis long to set
     */
    public SIPDate(long timeMillis) {
        javaCal =
            new GregorianCalendar(
                TimeZone.getTimeZone("GMT+8"),
                Locale.getDefault());
        java.util.Date date = new java.util.Date(timeMillis);
        javaCal.setTime(date);
        wkday = javaCal.get(Calendar.DAY_OF_WEEK);
        switch (wkday) {
            case Calendar.MONDAY :
                sipWkDay = MON;
                break;
            case Calendar.TUESDAY :
                sipWkDay = TUE;
                break;
            case Calendar.WEDNESDAY :
                sipWkDay = WED;
                break;
            case Calendar.THURSDAY :
                sipWkDay = THU;
                break;
            case Calendar.FRIDAY :
                sipWkDay = FRI;
                break;
            case Calendar.SATURDAY :
                sipWkDay = SAT;
                break;
            case Calendar.SUNDAY :
                sipWkDay = SUN;
                break;
            default :
                log.error("No date map for wkday " + wkday);
        }

        day = javaCal.get(Calendar.DAY_OF_MONTH);
        month = javaCal.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY :
                sipMonth = JAN;
                break;
            case Calendar.FEBRUARY :
                sipMonth = FEB;
                break;
            case Calendar.MARCH :
                sipMonth = MAR;
                break;
            case Calendar.APRIL :
                sipMonth = APR;
                break;
            case Calendar.MAY :
                sipMonth = MAY;
                break;
            case Calendar.JUNE :
                sipMonth = JUN;
                break;
            case Calendar.JULY :
                sipMonth = JUL;
                break;
            case Calendar.AUGUST :
                sipMonth = AUG;
                break;
            case Calendar.SEPTEMBER :
                sipMonth = SEP;
                break;
            case Calendar.OCTOBER :
                sipMonth = OCT;
                break;
            case Calendar.NOVEMBER :
                sipMonth = NOV;
                break;
            case Calendar.DECEMBER :
                sipMonth = DEC;
                break;
            default :
                log.error("No date map for month " + month);
        }
        year = javaCal.get(Calendar.YEAR);
        hour = javaCal.get(Calendar.HOUR_OF_DAY);
        minute = javaCal.get(Calendar.MINUTE);
        second = javaCal.get(Calendar.SECOND);
    }

    /**
     * Get canonical string representation.
     * @return String
     */
    public StringBuilder encode(StringBuilder encoding) {

        String dayString;
        if (day < 10) {
            dayString = "0" + day;
        } else
            dayString = "" + day;

        String hourString;
        if (hour < 10) {
            hourString = "0" + hour;
        } else
            hourString = "" + hour;

        String minuteString;
        if (minute < 10) {
            minuteString = "0" + minute;
        } else
            minuteString = "" + minute;

        String secondString;
        if (second < 10) {
            secondString = "0" + second;
        } else
            secondString = "" + second;
        
        if (sipWkDay != null)
            encoding .append(sipWkDay).append(Separators.COMMA).append(Separators.SP);

        encoding.append(dayString).append(Separators.SP);

        if (sipMonth != null)
            encoding.append(sipMonth).append(Separators.SP);

        return encoding.append(year)
            .append(Separators.SP)
            .append(hourString)
            .append(Separators.COLON)
            .append(minuteString)
            .append(Separators.COLON)
            .append(secondString)
            .append(Separators.SP)
            .append(GMT);
    }


    public Calendar getJavaCal() {
        if (javaCal == null)
            setJavaCal();
        return javaCal;
    }


    public String getWkday() {
        return sipWkDay;
    }


    public String getMonth() {
        return sipMonth;
    }


    public int getHour() {
        return hour;
    }


    public int getMinute() {
        return minute;
    }


    public int getSecond() {
        return second;
    }

    private void setJavaCal() {
        javaCal =
            new GregorianCalendar(
                TimeZone.getTimeZone("GMT+8"),
                Locale.getDefault());
        if (year != -1)
            javaCal.set(Calendar.YEAR, year);
        if (day != -1)
            javaCal.set(Calendar.DAY_OF_MONTH, day);
        if (month != -1)
            javaCal.set(Calendar.MONTH, month);
        if (wkday != -1)
            javaCal.set(Calendar.DAY_OF_WEEK, wkday);
        if (hour != -1)
            javaCal.set(Calendar.HOUR, hour);
        if (minute != -1)
            javaCal.set(Calendar.MINUTE, minute);
        if (second != -1)
            javaCal.set(Calendar.SECOND, second);
    }


    public void setWkday(String w) throws IllegalArgumentException {
        sipWkDay = w;
        if (sipWkDay.compareToIgnoreCase(MON) == 0) {
            wkday = Calendar.MONDAY;
        } else if (sipWkDay.compareToIgnoreCase(TUE) == 0) {
            wkday = Calendar.TUESDAY;
        } else if (sipWkDay.compareToIgnoreCase(WED) == 0) {
            wkday = Calendar.WEDNESDAY;
        } else if (sipWkDay.compareToIgnoreCase(THU) == 0) {
            wkday = Calendar.THURSDAY;
        } else if (sipWkDay.compareToIgnoreCase(FRI) == 0) {
            wkday = Calendar.FRIDAY;
        } else if (sipWkDay.compareToIgnoreCase(SAT) == 0) {
            wkday = Calendar.SATURDAY;
        } else if (sipWkDay.compareToIgnoreCase(SUN) == 0) {
            wkday = Calendar.SUNDAY;
        } else {
            throw new IllegalArgumentException("Illegal Week day :" + w);
        }
    }


    public void setDay(int d) throws IllegalArgumentException {
        if (d < 1 || d > 31)
            throw new IllegalArgumentException(
                "Illegal Day of the month " + Integer.toString(d));
        day = d;
    }


    public void setMonth(String m) throws IllegalArgumentException {
        sipMonth = m;
        if (sipMonth.compareToIgnoreCase(JAN) == 0) {
            month = Calendar.JANUARY;
        } else if (sipMonth.compareToIgnoreCase(FEB) == 0) {
            month = Calendar.FEBRUARY;
        } else if (sipMonth.compareToIgnoreCase(MAR) == 0) {
            month = Calendar.MARCH;
        } else if (sipMonth.compareToIgnoreCase(APR) == 0) {
            month = Calendar.APRIL;
        } else if (sipMonth.compareToIgnoreCase(MAY) == 0) {
            month = Calendar.MAY;
        } else if (sipMonth.compareToIgnoreCase(JUN) == 0) {
            month = Calendar.JUNE;
        } else if (sipMonth.compareToIgnoreCase(JUL) == 0) {
            month = Calendar.JULY;
        } else if (sipMonth.compareToIgnoreCase(AUG) == 0) {
            month = Calendar.AUGUST;
        } else if (sipMonth.compareToIgnoreCase(SEP) == 0) {
            month = Calendar.SEPTEMBER;
        } else if (sipMonth.compareToIgnoreCase(OCT) == 0) {
            month = Calendar.OCTOBER;
        } else if (sipMonth.compareToIgnoreCase(NOV) == 0) {
            month = Calendar.NOVEMBER;
        } else if (sipMonth.compareToIgnoreCase(DEC) == 0) {
            month = Calendar.DECEMBER;
        } else {
            throw new IllegalArgumentException("Illegal Month :" + m);
        }
    }


    public void setYear(int y) throws IllegalArgumentException {
        if (y < 0)
            throw new IllegalArgumentException("Illegal year : " + y);
        javaCal = null;
        year = y;
    }

    public int getYear() {
        return year;
    }


    public void setHour(int h) throws IllegalArgumentException {
        if (h < 0 || h > 24)
            throw new IllegalArgumentException("Illegal hour : " + h);
        javaCal = null;
        hour = h;
    }


    public void setMinute(int m) throws IllegalArgumentException {
        if (m < 0 || m >= 60)
            throw new IllegalArgumentException(
                "Illegal minute : " + (Integer.toString(m)));
        javaCal = null;
        minute = m;
    }


    public void setSecond(int s) throws IllegalArgumentException {
        if (s < 0 || s >= 60)
            throw new IllegalArgumentException(
                "Illegal second : " + Integer.toString(s));
        javaCal = null;
        second = s;
    }


    public int getDeltaSeconds() {
        // long ctime = this.getJavaCal().getTimeInMillis();
        long ctime = this.getJavaCal().getTime().getTime();
        return (int) (ctime - System.currentTimeMillis()) / 1000;
    }

    public Object clone() {
        SIPDate retval;
        try {
            retval = (SIPDate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Internal error");
        }
        if (javaCal != null)
            retval.javaCal = (Calendar) javaCal.clone();
        return retval;
    }
}
