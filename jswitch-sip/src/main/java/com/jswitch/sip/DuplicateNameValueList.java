package com.jswitch.sip;

import com.jswitch.common.constant.Separators;
import org.springframework.util.LinkedMultiValueMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danmo
 * @date 2024-06-18 13:57
 **/
public class DuplicateNameValueList implements Serializable, Cloneable {

    private LinkedMultiValueMap<String, NameValue> nameValueMap = new LinkedMultiValueMap<>();

    private static final long serialVersionUID = -1L;

    public DuplicateNameValueList() {
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (!nameValueMap.isEmpty()) {
            Iterator<NameValue> iterator = nameValueMap.values().stream().flatMap(Collection::stream).iterator();
            if (iterator.hasNext()) {
                while (true) {
                    Object obj = iterator.next();
                    if (obj instanceof GenericObject) {
                        GenericObject gobj = (GenericObject) obj;
                        gobj.encode(buffer);
                    } else {
                        buffer.append(obj.toString());
                    }
                    if (iterator.hasNext())
                        buffer.append(Separators.SEMICOLON);
                    else
                        break;
                }
            }
        }
        return buffer;
    }

    public String toString() {
        return this.encode();
    }


    public void set(NameValue nv) {
        this.nameValueMap.add(nv.getName().toLowerCase(), nv);
    }


    public void set(String name, Object value) {
        NameValue nameValue = new NameValue(name, value);
        this.nameValueMap.add(name, nameValue);
    }


    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (!otherObject.getClass().equals(this.getClass())) {
            return false;
        }
        DuplicateNameValueList other = (DuplicateNameValueList) otherObject;

        if (nameValueMap.size() != other.nameValueMap.size()) {
            return false;
        }
        Iterator<String> li = this.nameValueMap.keySet().iterator();

        while (li.hasNext()) {
            String key = (String) li.next();
            Collection nv1 = this.getNameValue(key);
            Collection nv2 = (Collection) other.nameValueMap.get(key);
            if (nv2 == null)
                return false;
            else if (!nv2.equals(nv1))
                return false;
        }
        return true;
    }


    public Object getValue(String name) {
        Collection nv = this.getNameValue(name.toLowerCase());
        if (nv != null)
            return nv;
        else
            return null;
    }


    public List<NameValue> getNameValue(String name) {
        return this.nameValueMap.get(name.toLowerCase());
    }


    public boolean hasNameValue(String name) {
        return nameValueMap.containsKey(name.toLowerCase());
    }


    public boolean delete(String name) {
        String lcName = name.toLowerCase();
        if (this.nameValueMap.containsKey(lcName)) {
            this.nameValueMap.remove(lcName);
            return true;
        } else {
            return false;
        }

    }

    public Object clone() {
        DuplicateNameValueList retval = new DuplicateNameValueList();
        Iterator<NameValue> it = this.nameValueMap.values().stream().flatMap(Collection::stream).iterator();
        while (it.hasNext()) {
            retval.set((NameValue) ((NameValue) it.next()).clone());
        }
        return retval;
    }

    public Iterator<NameValue> iterator() {
        return this.nameValueMap.values().stream().flatMap(Collection::stream).iterator();
    }

    public Iterator<String> getNames() {
        return this.nameValueMap.keySet().iterator();

    }


    public String getParameter(String name) {
        Object val = this.getValue(name);
        if (val == null)
            return null;
        if (val instanceof GenericObject)
            return ((GenericObject) val).encode();
        else
            return val.toString();
    }

    public void clear() {
        nameValueMap.clear();

    }

    public boolean isEmpty() {
        return this.nameValueMap.isEmpty();
    }

    public void put(String key, NameValue value) {
        this.nameValueMap.add(key, value);
    }

    public void remove(Object key) {
        this.nameValueMap.remove(key);
    }

    public int size() {
        return this.nameValueMap.size();
    }

    public List<NameValue> values() {
        return this.nameValueMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public int hashCode() {
        return this.nameValueMap.keySet().hashCode();
    }
}
