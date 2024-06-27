package com.jswitch.sip;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author danmo
 * @date 2024-06-18 10:35
 **/
@Slf4j
public abstract class GenericObject implements Serializable, Cloneable {

    protected static final Set<Class<?>> immutableClasses = new HashSet<Class<?>>(10);
    static final String[] immutableClassNames = {
            "String", "Character",
            "Boolean", "Byte", "Short", "Integer", "Long",
            "Float", "Double"
    };

    protected int indentation;
    protected String stringRepresentation;
    protected Match matchExpression;

    public void setMatcher(Match matchExpression) {
        if (matchExpression == null)
            throw new IllegalArgumentException("null arg!");
        this.matchExpression = matchExpression;
    }

    public Match getMatcher() {
        return matchExpression;
    }

    public static Class<?> getClassFromName(String className) {
        try {
            return Class.forName(className);
        } catch (Exception ex) {
            log.error("Exception: " + ex.getMessage());
            return null;
        }
    }

    public static boolean isMySubclass(Class<?> other) {

        return GenericObject.class.isAssignableFrom(other);

    }

    public static Object makeClone(Object obj) throws SecurityException {
        if (obj == null)
            throw new NullPointerException("null obj!");
        Class<?> c = obj.getClass();
        Object clone_obj = obj;
        if (immutableClasses.contains(c))
            return obj;
        else if (c.isArray()) {
            Class<?> ec = c.getComponentType();
            if (!ec.isPrimitive())
                clone_obj = ((Object[]) obj).clone();
            else {
                if (ec == Character.TYPE)
                    clone_obj = ((char[]) obj).clone();
                else if (ec == Boolean.TYPE)
                    clone_obj = ((boolean[]) obj).clone();
                if (ec == Byte.TYPE)
                    clone_obj = ((byte[]) obj).clone();
                else if (ec == Short.TYPE)
                    clone_obj = ((short[]) obj).clone();
                else if (ec == Integer.TYPE)
                    clone_obj = ((int[]) obj).clone();
                else if (ec == Long.TYPE)
                    clone_obj = ((long[]) obj).clone();
                else if (ec == Float.TYPE)
                    clone_obj = ((float[]) obj).clone();
                else if (ec == Double.TYPE)
                    clone_obj = ((double[]) obj).clone();
            }
        } else if (GenericObject.class.isAssignableFrom(c))
            clone_obj = ((GenericObject) obj).clone();
        else if (GenericObjectList.class.isAssignableFrom(c))
            clone_obj = ((GenericObjectList) obj).clone();
        else if (Cloneable.class.isAssignableFrom(c)) {
            try {
                Method meth = c.getMethod("clone", (Class[]) null);
                clone_obj = meth.invoke(obj, (Object[]) null);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                log.error("Exception: " + ex.getMessage());
            }
        }
        return clone_obj;
    }


    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Internal error");
        }
    }

    public void merge(Object mergeObject) {
        // Base case.
        if (mergeObject == null)
            return;

        if (!mergeObject.getClass().equals(this.getClass()))
            throw new IllegalArgumentException("Bad override object");

        Class<?> myclass = this.getClass();
        while (true) {
            Field[] fields = myclass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                int modifier = f.getModifiers();
                if (Modifier.isPrivate(modifier)) {
                    continue;
                } else if (Modifier.isStatic(modifier)) {
                    continue;
                } else if (Modifier.isInterface(modifier)) {
                    continue;
                }
                Class<?> fieldType = f.getType();
                String fname = fieldType.toString();
                try {
                    if (fieldType.isPrimitive()) {
                        if (fname.compareTo("int") == 0) {
                            int intfield = f.getInt(mergeObject);
                            f.setInt(this, intfield);
                        } else if (fname.compareTo("short") == 0) {
                            short shortField = f.getShort(mergeObject);
                            f.setShort(this, shortField);
                        } else if (fname.compareTo("char") == 0) {
                            char charField = f.getChar(mergeObject);
                            f.setChar(this, charField);
                        } else if (fname.compareTo("long") == 0) {
                            long longField = f.getLong(mergeObject);
                            f.setLong(this, longField);
                        } else if (fname.compareTo("boolean") == 0) {
                            boolean booleanField = f.getBoolean(mergeObject);
                            f.setBoolean(this, booleanField);
                        } else if (fname.compareTo("double") == 0) {
                            double doubleField = f.getDouble(mergeObject);
                            f.setDouble(this, doubleField);
                        } else if (fname.compareTo("float") == 0) {
                            float floatField = f.getFloat(mergeObject);
                            f.setFloat(this, floatField);
                        }
                    } else {
                        Object obj = f.get(this);
                        Object mobj = f.get(mergeObject);
                        if (mobj == null)
                            continue;
                        if (obj == null) {
                            f.set(this, mobj);
                            continue;
                        }
                        if (obj instanceof GenericObject) {
                            GenericObject gobj = (GenericObject) obj;
                            gobj.merge(mobj);
                        } else {
                            f.set(this, mobj);
                        }
                    }
                } catch (IllegalAccessException ex1) {
                    ex1.printStackTrace();
                    continue;
                }
            }
            myclass = myclass.getSuperclass();
            if (myclass.equals(GenericObject.class))
                break;
        }
    }

    protected GenericObject() {
        indentation = 0;
        stringRepresentation = "";
    }

    protected String getIndentation() {
        char[] chars = new char[indentation];
        java.util.Arrays.fill(chars, ' ');
        return new String(chars);
    }


    protected void sprint(String a) {
        if (a == null) {
            stringRepresentation += getIndentation();
            stringRepresentation += "<null>\n";
            return;
        }
        if (a.compareTo("}") == 0 || a.compareTo("]") == 0) {
            indentation--;
        }
        stringRepresentation += getIndentation();
        stringRepresentation += a;
        stringRepresentation += "\n";
        if (a.compareTo("{") == 0 || a.compareTo("[") == 0) {
            indentation++;
        }

    }

    protected void sprint(Object o) {
        sprint(o.toString());
    }

    protected void sprint(int intField) {
        sprint(String.valueOf(intField));
    }

    protected void sprint(short shortField) {
        sprint(String.valueOf(shortField));
    }


    protected void sprint(char charField) {
        sprint(String.valueOf(charField));

    }


    protected void sprint(long longField) {
        sprint(String.valueOf(longField));
    }


    protected void sprint(boolean booleanField) {
        sprint(String.valueOf(booleanField));
    }


    protected void sprint(double doubleField) {
        sprint(String.valueOf(doubleField));
    }


    protected void sprint(float floatField) {
        sprint(String.valueOf(floatField));
    }


    protected void dbgPrint() {
        log.debug(debugDump());
    }

    protected void dbgPrint(String s) {
        log.debug(s);
    }

    public boolean equals(Object that) {
        if (that == null) return false;
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
                        return true;
                    else if (f.get(this) == null)
                        return false;
                    else if (g.get(that) == null)
                        return false;
                    else if (g.get(that) == null && f.get(this) != null)
                        return false;
                    else if (!f.get(this).equals(g.get(that)))
                        return false;
                } catch (IllegalAccessException ex1) {
                    log.error("IllegalAccessException:{}", ex1.getMessage(), ex1);
                }
            }
            if (myclass.equals(GenericObject.class))
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
        Class<?> myclass = this.getClass();
        Field[] fields = myclass.getDeclaredFields();
        Class<?> hisclass = other.getClass();
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
                        if ((((String) hisObj).trim()).equals(""))
                            continue;
                        if (((String) myObj)
                                .compareToIgnoreCase((String) hisObj)
                                != 0)
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
            } catch (Exception ex) {
                log.error("debugDump Exception:{}", ex.getMessage(), ex);
            }
        }
        sprint("}");
        return stringRepresentation;
    }

    public String debugDump(int indent) {
        indentation = indent;
        String retval = this.debugDump();
        indentation = 0;
        return retval;
    }

    public abstract String encode();


    public StringBuilder encode(StringBuilder buffer) {
        return buffer.append(encode());
    }
}
