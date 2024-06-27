package com.jswitch.sip.header;

import com.jswitch.common.constant.Separators;
import com.jswitch.sip.GenericObject;
import com.jswitch.sip.SIPHeaderNames;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author danmo
 * @date 2024-06-18 16:02
 **/
public abstract class SIPHeaderList<HDR extends SIPHeader> extends SIPHeader implements List<HDR>, Header {

    private static boolean prettyEncode = false;
    /**
     * hlist field.
     */
    protected List<HDR> hlist;

    private Class<HDR> myClass;

    public String getName() {
        return this.headerName;
    }


    private SIPHeaderList() {
        hlist = new LinkedList<HDR>();
    }

    /**
     * Constructor
     *
     * @param objclass Class to set
     * @param hname    String to set
     */
    protected SIPHeaderList(Class<HDR> objclass, String hname) {
        this();
        this.headerName = hname;
        this.myClass = objclass;
    }

    /**
     * Concatenate the list of stuff that we are keeping around and also the
     * text corresponding to these structures (that we parsed).
     *
     * @param objectToAdd
     */
    public boolean add(HDR objectToAdd) {
        hlist.add((HDR) objectToAdd);
        return true;
    }

    /**
     * Concatenate the list of stuff that we are keeping around and also the
     * text corresponding to these structures (that we parsed).
     *
     * @param obj Genericobject to set
     */
    public void addFirst(HDR obj) {
        hlist.add(0, (HDR) obj);
    }

    /**
     * Add to this list.
     *
     * @param sipheader SIPHeader to add.
     * @param top       is true if we want to add to the top of the list.
     */
    public void add(HDR sipheader, boolean top) {
        if (top)
            this.addFirst(sipheader);
        else
            this.add(sipheader);
    }

    /**
     * Concatenate two compatible lists. This appends or prepends the new list
     * to the end of this list.
     *
     * @param other   SIPHeaderList to set
     * @param topFlag flag which indicates which end to concatenate
     *                the lists.
     * @throws IllegalArgumentException if the two lists are not compatible
     */
    public void concatenate(SIPHeaderList<HDR> other, boolean topFlag)
            throws IllegalArgumentException {
        if (!topFlag) {
            this.addAll(other);
        } else {
            // add given items to the top end of the list.
            this.addAll(0, other);
        }

    }


    /**
     * Encode a list of sip headers. Headers are returned in cannonical form.
     *
     * @return String encoded string representation of this list of headers.
     * (Contains string append of each encoded header).
     */
    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (hlist.isEmpty()) {
            buffer.append(headerName).append(':').append(Separators.NEWLINE);
        } else {
            if (this.headerName.equals(SIPHeaderNames.WWW_AUTHENTICATE)
                    || this.headerName.equals(SIPHeaderNames.PROXY_AUTHENTICATE)
                    || this.headerName.equals(SIPHeaderNames.AUTHORIZATION)
                    || this.headerName.equals(SIPHeaderNames.PROXY_AUTHORIZATION)
                    || (prettyEncode &&
                    (this.headerName.equals(SIPHeaderNames.VIA) || this.headerName.equals(SIPHeaderNames.ROUTE) || this.headerName.equals(SIPHeaderNames.RECORD_ROUTE))) // Less confusing to read
                    || this.getClass().equals(ExtensionHeaderList.class)) {
                ListIterator<HDR> li = hlist.listIterator();
                while (li.hasNext()) {
                    HDR sipheader = (HDR) li.next();
                    sipheader.encode(buffer);
                }
            } else {
                buffer.append(headerName).append(Separators.COLON).append(Separators.SP);
                this.encodeBody(buffer);
                buffer.append(Separators.NEWLINE);
            }
        }
        return buffer;
    }


    public List<String> getHeadersAsEncodedStrings() {
        List<String> retval = new LinkedList<String>();

        ListIterator<HDR> li = hlist.listIterator();
        while (li.hasNext()) {
            Header sipheader = (Header) li.next();
            retval.add(sipheader.toString());
        }
        return retval;

    }


    public Header getFirst() {
        if (hlist == null || hlist.isEmpty())
            return null;
        else
            return (Header) hlist.get(0);
    }


    public Header getLast() {
        if (hlist == null || hlist.isEmpty())
            return null;
        return (Header) hlist.get(hlist.size() - 1);
    }

    public Class<HDR> getMyClass() {
        return this.myClass;
    }


    public boolean isEmpty() {
        return hlist.isEmpty();
    }


    public ListIterator<HDR> listIterator() {

        return hlist.listIterator(0);
    }


    public List<HDR> getHeaderList() {
        return this.hlist;
    }


    public ListIterator<HDR> listIterator(int position) {
        return hlist.listIterator(position);
    }


    public void removeFirst() {
        if (!hlist.isEmpty())
            hlist.remove(0);

    }


    public void removeLast() {
        if (!hlist.isEmpty())
            hlist.remove(hlist.size() - 1);
    }


    public boolean remove(HDR obj) {
        if (hlist.isEmpty())
            return false;
        else
            return hlist.remove(obj);
    }


    protected void setMyClass(Class<HDR> cl) {
        this.myClass = cl;
    }


    public String debugDump(int indentation) {
        stringRepresentation = "";
        String indent = new Indentation(indentation).getIndentation();

        String className = this.getClass().getName();
        sprint(indent + className);
        sprint(indent + "{");

        for (HDR sipHeader : hlist) {
            sprint(indent + sipHeader.debugDump());
        }
        sprint(indent + "}");
        return stringRepresentation;
    }


    public String debugDump() {
        return debugDump(0);
    }


    public Object[] toArray() {
        return hlist.toArray();

    }


    public int indexOf(GenericObject gobj) {
        return hlist.indexOf(gobj);
    }


    public void add(int index, HDR sipHeader)
            throws IndexOutOfBoundsException {
        hlist.add(index, sipHeader);
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {

        if (other == this)
            return true;

        if (other instanceof SIPHeaderList) {
            SIPHeaderList<SIPHeader> that = (SIPHeaderList<SIPHeader>) other;
            if (this.hlist == that.hlist)
                return true;
            else if (this.hlist == null)
                return that.hlist.isEmpty();
            return this.hlist.equals(that.hlist);
        }
        return false;
    }


    public boolean match(SIPHeaderList<?> template) {
        if (template == null)
            return true;
        if (!this.getClass().equals(template.getClass()))
            return false;
        SIPHeaderList<SIPHeader> that = (SIPHeaderList<SIPHeader>) template;
        if (this.hlist == that.hlist)
            return true;
        else if (this.hlist == null)
            return false;
        else {

            for (Iterator<SIPHeader> it = that.hlist.iterator(); it.hasNext(); ) {
                SIPHeader sipHeader = (SIPHeader) it.next();

                boolean found = false;
                for (Iterator<HDR> it1 = this.hlist.iterator(); it1.hasNext()
                        && !found; ) {
                    SIPHeader sipHeader1 = (SIPHeader) it1.next();
                    found = sipHeader1.match(sipHeader);
                }
                if (!found)
                    return false;
            }
            return true;
        }
    }


    public Object clone() {
        try {
            Class<?> clazz = this.getClass();

            Constructor<?> cons = clazz.getConstructor((Class[]) null);
            SIPHeaderList<HDR> retval = (SIPHeaderList<HDR>) cons.newInstance((Object[]) null);
            retval.headerName = this.headerName;
            retval.myClass = this.myClass;
            return retval.clonehlist(this.hlist);
        } catch (Exception ex) {
            throw new RuntimeException("Could not clone!", ex);
        }
    }

    protected final SIPHeaderList<HDR> clonehlist(List<HDR> hlistToClone) {
        if (hlistToClone != null) {
            for (Iterator<HDR> it = (Iterator<HDR>) hlistToClone.iterator(); it.hasNext(); ) {
                HDR h = it.next();
                this.hlist.add((HDR) h.clone());
            }
        }
        return this;
    }


    public int size() {
        return hlist.size();
    }


    public boolean isHeaderList() {
        return true;
    }

    protected String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        ListIterator<HDR> iterator = this.listIterator();
        while (true) {
            SIPHeader sipHeader = (SIPHeader) iterator.next();
            if (sipHeader == this) throw new RuntimeException("Unexpected circularity in SipHeaderList");
            sipHeader.encodeBody(buffer);
            if (iterator.hasNext()) {
                if (!this.headerName.equals(PrivacyHeader.NAME))
                    buffer.append(Separators.COMMA);
                else
                    buffer.append(Separators.SEMICOLON);
                continue;
            } else
                break;

        }
        return buffer;
    }

    public boolean addAll(Collection<? extends HDR> collection) {
        return this.hlist.addAll(collection);
    }

    public boolean addAll(int index, Collection<? extends HDR> collection) {
        return this.hlist.addAll(index, collection);

    }

    public boolean containsAll(Collection<?> collection) {
        return this.hlist.containsAll(collection);
    }


    public void clear() {
        this.hlist.clear();
    }

    public boolean contains(Object header) {
        return this.hlist.contains(header);
    }


    public HDR get(int index) {
        return this.hlist.get(index);
    }


    public int indexOf(Object obj) {
        return this.hlist.indexOf(obj);
    }


    public java.util.Iterator<HDR> iterator() {
        return this.hlist.listIterator();
    }

    public int lastIndexOf(Object obj) {

        return this.hlist.lastIndexOf(obj);
    }

    public boolean remove(Object obj) {

        return this.hlist.remove(obj);
    }

    public HDR remove(int index) {
        return this.hlist.remove(index);
    }


    public boolean removeAll(java.util.Collection<?> collection) {
        return this.hlist.removeAll(collection);
    }


    public boolean retainAll(java.util.Collection<?> collection) {
        return this.hlist.retainAll(collection);
    }


    public java.util.List<HDR> subList(int index1, int index2) {
        return this.hlist.subList(index1, index2);

    }


    public int hashCode() {

        return this.headerName.hashCode();
    }


    public HDR set(int position, HDR sipHeader) {

        return hlist.set(position, sipHeader);

    }

    public static void setPrettyEncode(boolean flag) {
        prettyEncode = flag;
    }


    public <T> T[] toArray(T[] array) {
        return this.hlist.toArray(array);
    }

}
