package com.jswitch.sip;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author danmo
 * @date 2024-06-18 10:50
 **/
@Slf4j
public abstract class NetObject extends GenericObject {

    protected static final String UDP = "udp";
    protected static final String TCP = "tcp";
    protected static final String TRANSPORT = "transport";
    protected static final String METHOD = "method";
    protected static final String USER = "user";
    protected static final String PHONE = "phone";
    protected static final String MADDR = "maddr";
    protected static final String TTL = "ttl";
    protected static final String LR = "lr";
    protected static final String SIP = "sip";
    protected static final String SIPS = "sips";

    protected static final String TLS = "tls";

    protected static final String GRUU = "gr";


    public NetObject() {
        super();
    }

    public boolean equals(Object that) {
        if (!this.getClass().equals(that.getClass()))
            return false;
        Class<?> myclass = this.getClass();
        Class<?> hisclass = that.getClass();
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
                Class<?> fieldType = f.getType();
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
                    else if (g.get(that) == null && f.get(that) != null)
                        return false;
                    else if (!f.get(this).equals(g.get(that)))
                        return false;
                } catch (IllegalAccessException ex1) {
                    log.error("IllegalAccessException:{}", ex1.getMessage(), ex1);
                }
            }
            if (myclass.equals(NetObject.class))
                break;
            else {
                myclass = myclass.getSuperclass();
                hisclass = hisclass.getSuperclass();
            }
        }
        return true;
    }


    public boolean match(Object other) {
        if (other == null)
            return true;
        if (!this.getClass().equals(other.getClass()))
            return false;
        GenericObject that = (GenericObject) other;
        // System.out.println("Comparing " + that.encode());
        // System.out.println("this = " + this.encode());

        Class<?> hisclass = other.getClass();
        Class<?> myclass = this.getClass();
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
                Class<?> fieldType = f.getType();
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
                            if (((String) hisObj).equals(""))
                                continue;
                            if (((String) myObj)
                                    .compareToIgnoreCase((String) hisObj)
                                    != 0)
                                return false;
                        } else if (
                                GenericObject.isMySubclass(myObj.getClass())
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
            if (myclass.equals(NetObject.class))
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
        Class<?> myclass = getClass();
        sprint(myclass.getName());
        sprint("{");
        Field[] fields = myclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            int modifier = f.getModifiers();
            if ((modifier & Modifier.PRIVATE) == Modifier.PRIVATE)
                continue;
            Class<?> fieldType = f.getType();
            String fieldName = f.getName();
            if (fieldName.compareTo("stringRepresentation") == 0) {
                continue;
            }
            if (fieldName.compareTo("indentation") == 0) {
                continue;
            }
            sprint(fieldName + ":");
            try {
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
