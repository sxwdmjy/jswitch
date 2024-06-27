package com.jswitch.sip;

import com.jswitch.common.constant.Separators;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author danmo
 * @date 2024-06-18 10:44
 **/
@Slf4j
public abstract class GenericObjectList extends LinkedList<GenericObject> implements Serializable, Cloneable{

    protected int indentation;

    protected String listName; // For debugging

    private ListIterator<? extends GenericObject> myListIterator;

    private String stringRep;

    protected Class<?> myClass;

    protected String getIndentation() {
        char[] chars = new char[indentation];
        java.util.Arrays.fill(chars, ' ');
        return new String(chars);
    }

    protected static boolean isCloneable(Object obj) {
        return obj instanceof Cloneable;
    }

    public static boolean isMySubclass(Class<?> other) {
        return GenericObjectList.class.isAssignableFrom(other);

    }

    public Object clone() {
        GenericObjectList retrieval = (GenericObjectList) super.clone();
        for (ListIterator<GenericObject> iter = retrieval.listIterator(); iter.hasNext();) {
            GenericObject obj = (GenericObject) ((GenericObject) iter.next())
                    .clone();
            iter.set(obj);
        }
        return retrieval;
    }



    public void setMyClass(Class cl) {
        myClass = cl;
    }

    protected GenericObjectList() {
        super();
        listName = null;
        stringRep = "";
    }

    protected GenericObjectList(String lname) {
        this();
        listName = lname;
    }


    protected GenericObjectList(String lname, String classname) {
        this(lname);
        try {
            myClass = Class.forName(classname);
        } catch (ClassNotFoundException ex) {
            log.error("Class not found: " + classname);
        }

    }



    protected GenericObjectList(String lname, Class objclass) {
        this(lname);
        myClass = objclass;
    }


    protected GenericObject next(ListIterator iterator) {
        try {
            return (GenericObject) iterator.next();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    protected GenericObject first() {
        myListIterator = this.listIterator(0);
        try {
            return (GenericObject) myListIterator.next();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }


    protected GenericObject next() {
        if (myListIterator == null) {
            myListIterator = this.listIterator(0);
        }
        try {
            return (GenericObject) myListIterator.next();
        } catch (NoSuchElementException ex) {
            myListIterator = null;
            return null;
        }
    }


    protected void concatenate(GenericObjectList objList) {
        concatenate(objList, false);
    }


    protected void concatenate(GenericObjectList objList, boolean topFlag) {
        if (!topFlag) {
            this.addAll(objList);
        } else {
            // add given items to the top end of the list.
            this.addAll(0, objList);
        }
    }

    /**
     * string formatting function.
     */

    private void sprint(String s) {
        if (s == null) {
            stringRep += getIndentation();
            stringRep += "<null>\n";
            return;
        }

        if (s.compareTo("}") == 0 || s.compareTo("]") == 0) {
            indentation--;
        }
        stringRep += getIndentation();
        stringRep += s;
        stringRep += "\n";
        if (s.compareTo("{") == 0 || s.compareTo("[") == 0) {
            indentation++;
        }
    }

    /**
     * Convert this list of headers to a formatted string.
     */

    public String debugDump() {
        stringRep = "";
        Object obj = this.first();
        if (obj == null)
            return "<null>";
        sprint("listName:");
        sprint(listName);
        sprint("{");
        while (obj != null) {
            sprint("[");
            sprint(((GenericObject) obj).debugDump(this.indentation));
            obj = next();
            sprint("]");
        }
        sprint("}");
        return stringRep;
    }

    /**
     * Convert this list of headers to a string (for printing) with an
     * indentation given.
     */

    public String debugDump(int indent) {
        int save = indentation;
        indentation = indent;
        String retval = this.debugDump();
        indentation = save;
        return retval;
    }

    public void addFirst(GenericObject objToAdd) {
        if (myClass == null) {
            myClass = objToAdd.getClass();
        } else {
            super.addFirst(objToAdd);
        }
    }

    /**
     * Do a merge of the GenericObjects contained in this list with the
     * GenericObjects in the mergeList. Note that this does an inplace
     * modification of the given list. This does an object by object merge of
     * the given objects.
     *
     * @param mergeList
     *            is the list of Generic objects that we want to do an object by
     *            object merge with. Note that no new objects are added to this
     *            list.
     *
     */

    public void mergeObjects(GenericObjectList mergeList) {

        if (mergeList == null)
            return;
        Iterator it1 = this.listIterator();
        Iterator it2 = mergeList.listIterator();
        while (it1.hasNext()) {
            GenericObject outerObj = (GenericObject) it1.next();
            while (it2.hasNext()) {
                Object innerObj = it2.next();
                outerObj.merge(innerObj);
            }
        }
    }

    /**
     * Encode the list in semicolon separated form.
     *
     * @return an encoded string containing the objects in this list.
     * @since v1.0
     */
    public String encode() {
        if (this.isEmpty())
            return "";
        StringBuilder encoding = new StringBuilder();
        ListIterator iterator = this.listIterator();
        if (iterator.hasNext()) {
            while (true) {
                Object obj = iterator.next();
                if (obj instanceof GenericObject) {
                    GenericObject gobj = (GenericObject) obj;
                    encoding.append(gobj.encode());
                } else {
                    encoding.append(obj.toString());
                }
                if (iterator.hasNext())
                    encoding.append(Separators.SEMICOLON);
                else
                    break;
            }
        }
        return encoding.toString();
    }

    /**
     * Alias for the encode function above.
     */
    public String toString() {
        return this.encode();
    }

    /**
     * Hash code. We never expect to put this in a hash table so return a constant.
     */
    public int hashCode() { return 42; }

    /**
     * Equality checking predicate.
     *
     * @param other
     *            is the object to compare ourselves to.
     * @return true if the objects are equal.
     */
    public boolean equals(Object other) {
        if (other == null ) return false;
        if (!this.getClass().equals(other.getClass()))
            return false;
        GenericObjectList that = (GenericObjectList) other;
        if (this.size() != that.size())
            return false;
        ListIterator myIterator = this.listIterator();
        while (myIterator.hasNext()) {
            Object myobj = myIterator.next();
            ListIterator hisIterator = that.listIterator();
            try {
                while (true) {
                    Object hisobj = hisIterator.next();
                    if (myobj.equals(hisobj))
                        break;
                }
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
        ListIterator hisIterator = that.listIterator();
        while (hisIterator.hasNext()) {
            Object hisobj = hisIterator.next();
            myIterator = this.listIterator();
            try {
                while (true) {
                    Object myobj = myIterator.next();
                    if (hisobj.equals(myobj))
                        break;
                }
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
        return true;
    }

    /**
     * Match with a template (return true if we have a superset of the given
     * template. This can be used for partial match (template matching of SIP
     * objects). Note -- this implementation is not unnecessarily efficient :-)
     *
     * @param other
     *            template object to compare against.
     */

    public boolean match(Object other) {
        if (!this.getClass().equals(other.getClass()))
            return false;
        GenericObjectList that = (GenericObjectList) other;
        ListIterator hisIterator = that.listIterator();
        outer: while (hisIterator.hasNext()) {
            Object hisobj = hisIterator.next();
            Object myobj = null;
            ListIterator myIterator = this.listIterator();
            while (myIterator.hasNext()) {
                myobj = myIterator.next();
                if (myobj instanceof GenericObject)
                    System.out.println("Trying to match  = "
                            + ((GenericObject) myobj).encode());
                if (GenericObject.isMySubclass(myobj.getClass())
                        && ((GenericObject) myobj).match(hisobj))
                    break outer;
                else if (GenericObjectList.isMySubclass(myobj.getClass())
                        && ((GenericObjectList) myobj).match(hisobj))
                    break outer;
            }
            System.out.println(((GenericObject) hisobj).encode());
            return false;
        }
        return true;
    }
}
