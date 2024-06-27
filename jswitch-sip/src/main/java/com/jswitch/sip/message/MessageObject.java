package com.jswitch.sip.message;

import com.jswitch.sip.GenericObject;
import com.jswitch.sip.GenericObjectList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author danmo
 * @date 2024-06-18 13:50
 **/
public abstract class MessageObject extends GenericObject {

    public abstract String encode();

    public void dbgPrint() {
        super.dbgPrint();
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
            if (modifier == Modifier.PRIVATE)
                continue;
            Class<?> fieldType = f.getType();
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
                } else if (
                        GenericObject.class.isAssignableFrom(
                                fieldType)) {
                    if (f.get(this) != null) {
                        sprint(
                                ((GenericObject) f.get(this)).debugDump(
                                        this.indentation + 1));
                    } else {
                        sprint("<null>");
                    }

                } else if (GenericObjectList.class.isAssignableFrom(
                        fieldType)) {
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
                continue; // we are accessing a private field...
            }
        }
        sprint("}");
        return stringRepresentation;
    }


    protected MessageObject() {
        super();
    }

    /**
     * Formatter with a given starting indentation (for nested structs).
     */
    public String dbgPrint(int indent) {
        int save = indentation;
        indentation = indent;
        String retval = this.toString();
        indentation = save;
        return retval;
    }
}
