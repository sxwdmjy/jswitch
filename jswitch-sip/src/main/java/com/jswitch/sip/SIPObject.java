package com.jswitch.sip;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author danmo
 * @date 2024-06-18 14:30
 **/
@Slf4j
public abstract class SIPObject extends GenericObject {

    protected SIPObject() {
        super();
    }



    /** Debug function
     */
    public void dbgPrint() {
        super.dbgPrint();
    }

    /** Encode the header into a String.
     * @return String
     */
    public abstract String encode();

    /** Encode the header into the given StringBuilder.
     * Default implemation calls encode().
     */
    public StringBuilder encode(StringBuilder buffer) {
        return buffer.append(encode());
    }

    /**
     * An introspection based equality predicate for SIPObjects.
     *@param other the other object to test against.
     */
    public boolean equals(Object other) {
        if (!this.getClass().equals(other.getClass()))
            return false;
        SIPObject that = (SIPObject) other;
        Class myclass = this.getClass();
        Class hisclass = other.getClass();
        while (true) {
            Field[] fields = myclass.getDeclaredFields();
            if (!hisclass.equals(myclass))
                return false;
            Field[] hisfields = hisclass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                Field g = hisfields[i];
                // Only print protected and public members.
                int modifier = f.getModifiers();
                if ((modifier & Modifier.PRIVATE) == Modifier.PRIVATE)
                    continue;
                Class fieldType = f.getType();
                String fieldName = f.getName();
                if (fieldName.compareTo("stringRepresentation") == 0) {
                    continue;
                }
                if (fieldName.compareTo("indentation") == 0) {
                    continue;
                }
                try {
                    // Primitive fields are printed with type: value
                    if (fieldType.isPrimitive()) {
                        String fname = fieldType.toString();
                        if (fname.compareTo("int") == 0) {
                            if (f.getInt(this) != g.getInt(that))
                                return false;
                        } else if (fname.compareTo("short") == 0) {
                            if (f.getShort(this) != g.getShort(that))
                                return false;
                        } else if (fname.compareTo("char") == 0) {
                            if (f.getChar(this) != g.getChar(that))
                                return false;
                        } else if (fname.compareTo("long") == 0) {
                            if (f.getLong(this) != g.getLong(that))
                                return false;
                        } else if (fname.compareTo("boolean") == 0) {
                            if (f.getBoolean(this) != g.getBoolean(that))
                                return false;
                        } else if (fname.compareTo("double") == 0) {
                            if (f.getDouble(this) != g.getDouble(that))
                                return false;
                        } else if (fname.compareTo("float") == 0) {
                            if (f.getFloat(this) != g.getFloat(that))
                                return false;
                        }
                    } else if (g.get(that) == f.get(this))
                        continue;
                    else if (f.get(this) == null && g.get(that) != null)
                        return false;
                    else if (g.get(that) == null && f.get(this) != null)
                        return false;
                    else if (!f.get(this).equals(g.get(that)))
                        return false;
                } catch (IllegalAccessException ex1) {
                    log.info("accessed field " + fieldName);
                    log.info("modifier  " + modifier);
                    log.info("modifier.private  " + Modifier.PRIVATE);
                    log.error("IllegalAccessException:{}", ex1.getMessage(), ex1);
                }
            }
            if (myclass.equals(SIPObject.class))
                break;
            else {
                myclass = myclass.getSuperclass();
                hisclass = hisclass.getSuperclass();
            }
        }
        return true;
    }

    /** An introspection based predicate matching using a template
     * object. Allows for partial match of two protocl Objects.
     * You can set a generalized matcher (using regular expressions
     * for example) by implementing the Match interface and registering
     * it with the template.
     *@param other the match pattern to test against. The match object
     * has to be of the same type (class). Primitive types
     * and non-sip fields that are non null are matched for equality.
     * Null in any field  matches anything. Some book-keeping fields
     * are ignored when making the comparison.
     *
     */
    public boolean match(Object other) {
        if (other == null) {
            return true;
        }

        if (!this.getClass().equals(other.getClass()))
            return false;
        GenericObject that = (GenericObject) other;
        Class myclass = this.getClass();
        Class hisclass = other.getClass();
        while (true) {
            Field[] fields = myclass.getDeclaredFields();
            Field[] hisfields = hisclass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                Field g = hisfields[i];
                // Only print protected and public members.
                int modifier = f.getModifiers();
                if ((modifier & Modifier.PRIVATE) == Modifier.PRIVATE)
                    continue;
                Class fieldType = f.getType();
                String fieldName = f.getName();
                if (fieldName.compareTo("stringRepresentation") == 0) {
                    continue;
                }
                if (fieldName.compareTo("indentation") == 0) {
                    continue;
                }
                try {
                    if (fieldType.isPrimitive()) {
                        String fname = fieldType.toString();
                        if (fname.compareTo("int") == 0) {
                            if (f.getInt(this) != g.getInt(that))
                                return false;
                        } else if (fname.compareTo("short") == 0) {
                            if (f.getShort(this) != g.getShort(that))
                                return false;
                        } else if (fname.compareTo("char") == 0) {
                            if (f.getChar(this) != g.getChar(that))
                                return false;
                        } else if (fname.compareTo("long") == 0) {
                            if (f.getLong(this) != g.getLong(that))
                                return false;
                        } else if (fname.compareTo("boolean") == 0) {
                            if (f.getBoolean(this) != g.getBoolean(that))
                                return false;
                        } else if (fname.compareTo("double") == 0) {
                            if (f.getDouble(this) != g.getDouble(that))
                                return false;
                        } else if (fname.compareTo("float") == 0) {
                            if (f.getFloat(this) != g.getFloat(that))
                                return false;
                        } else {
                            log.error("unknown type");
                        }
                    } else {
                        Object myObj = f.get(this);
                        Object hisObj = g.get(that);
                        if (hisObj != null && myObj == null)
                            return false;
                        else if (hisObj == null && myObj != null)
                            continue;
                        else if (hisObj == null && myObj == null)
                            continue;
                        else if (
                                hisObj instanceof java.lang.String
                                        && myObj instanceof java.lang.String) {
                            if ((((String) hisObj).trim()).equals(""))
                                continue;
                            if (((String) myObj)
                                    .compareToIgnoreCase((String) hisObj)
                                    != 0)
                                return false;
                        } else if (
                                hisObj != null
                                        && GenericObject.isMySubclass(myObj.getClass())
                                        && GenericObject.isMySubclass(hisObj.getClass())
                                        && myObj.getClass().equals(hisObj.getClass())
                                        && ((GenericObject) hisObj).getMatcher()
                                        != null) {
                            String myObjEncoded =
                                    ((GenericObject) myObj).encode();
                            boolean retval =
                                    ((GenericObject) hisObj).getMatcher().match(
                                            myObjEncoded);
                            if (!retval)
                                return false;
                        } else if (
                                GenericObject.isMySubclass(myObj.getClass())
                                        && !((GenericObject) myObj).match(hisObj))
                            return false;
                        else if (
                                GenericObjectList.isMySubclass(myObj.getClass())
                                        && !((GenericObjectList) myObj).match(hisObj))
                            return false;

                    }
                } catch (IllegalAccessException ex1) {
                    log.error("IllegalAccessException:{}", ex1.getMessage(), ex1);
                }
            }
            if (myclass.equals(SIPObject.class))
                break;
            else {
                myclass = myclass.getSuperclass();
                hisclass = hisclass.getSuperclass();
            }
        }
        return true;
    }


    public String debugDump() {
        stringRepresentation = "";
        Class myclass = getClass();
        sprint(myclass.getName());
        sprint("{");
        Field[] fields = myclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            // Only print protected and public members.
            int modifier = f.getModifiers();
            if ((modifier & Modifier.PRIVATE) == Modifier.PRIVATE)
                continue;
            Class fieldType = f.getType();
            String fieldName = f.getName();
            if (fieldName.compareTo("stringRepresentation") == 0) {
                // avoid nasty recursions...
                continue;
            }
            if (fieldName.compareTo("indentation") == 0) {
                // formatting stuff - not relevant here.
                continue;
            }
            sprint(fieldName + ":");
            try {
                // Primitive fields are printed with type: value
                if (fieldType.isPrimitive()) {
                    String fname = fieldType.toString();
                    sprint(fname + ":");
                    if (fname.compareTo("int") == 0) {
                        int intfield = f.getInt(this);
                        sprint(intfield);
                    } else if (fname.compareTo("short") == 0) {
                        short shortField = f.getShort(this);
                        sprint(shortField);
                    } else if (fname.compareTo("char") == 0) {
                        char charField = f.getChar(this);
                        sprint(charField);
                    } else if (fname.compareTo("long") == 0) {
                        long longField = f.getLong(this);
                        sprint(longField);
                    } else if (fname.compareTo("boolean") == 0) {
                        boolean booleanField = f.getBoolean(this);
                        sprint(booleanField);
                    } else if (fname.compareTo("double") == 0) {
                        double doubleField = f.getDouble(this);
                        sprint(doubleField);
                    } else if (fname.compareTo("float") == 0) {
                        float floatField = f.getFloat(this);
                        sprint(floatField);
                    }
                } else if (GenericObject.class.isAssignableFrom(fieldType)) {
                    if (f.get(this) != null) {
                        sprint(
                                ((GenericObject) f.get(this)).debugDump(
                                        indentation + 1));
                    } else {
                        sprint("<null>");
                    }

                } else if (
                        GenericObjectList.class.isAssignableFrom(fieldType)) {
                    if (f.get(this) != null) {
                        sprint(
                                ((GenericObjectList) f.get(this)).debugDump(
                                        indentation + 1));
                    } else {
                        sprint("<null>");
                    }

                } else {
                    // Dont do recursion on things that are not
                    // of our header type...
                    if (f.get(this) != null) {
                        sprint(f.get(this).getClass().getName() + ":");
                    } else {
                        sprint(fieldType.getName() + ":");
                    }

                    sprint("{");
                    if (f.get(this) != null) {
                        sprint(f.get(this).toString());
                    } else {
                        sprint("<null>");
                    }
                    sprint("}");
                }
            } catch (IllegalAccessException ex1) {
                continue;
            }
        }
        sprint("}");
        return stringRepresentation;
    }


    public String debugDump(int indent) {
        int save = indentation;
        indentation = indent;
        String retval = this.debugDump();
        indentation = save;
        return retval;
    }


    public String toString() {
        return this.encode();
    }
}
