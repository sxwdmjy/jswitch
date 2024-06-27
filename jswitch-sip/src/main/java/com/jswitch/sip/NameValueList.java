package com.jswitch.sip;

import com.jswitch.common.constant.Separators;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author danmo
 * @date 2024-06-18 11:11
 **/
@Data
public class NameValueList implements Serializable, Cloneable, Map<String,NameValue> {
    private static final long serialVersionUID = 1L;

    private Map<String,NameValue> hmap;

    private String separator;

    private boolean sync = false;

    public NameValueList() {
        this.separator = Separators.SEMICOLON;
    }
    public NameValueList(boolean sync) {
        this.separator = Separators.SEMICOLON;
        this.sync = sync;
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (!this.isEmpty()) {
            Iterator<NameValue> iterator = this.iterator();
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
                        buffer.append(separator);
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
        this.put(nv.getName().toLowerCase(), nv);
    }

    public void set(String name, Object value) {
        NameValue nameValue = new NameValue(name, value);
        this.put(name.toLowerCase(), nameValue);

    }


    public boolean equals(Object otherObject) {
        if ( otherObject == null ) {
            return false;
        }
        if (!otherObject.getClass().equals(this.getClass())) {
            return false;
        }
        NameValueList other = (NameValueList) otherObject;

        Iterator<String> li = this.getNames();

        while (li.hasNext()) {
            String key = (String) li.next();
            NameValue nv1 = this.getNameValue(key);
            NameValue nv2 = (NameValue) other.get(key);
            if (nv2 == null)
                return false;
            else if (!nv2.equals(nv1))
                return false;
        }
        return true;
    }


    public Object getValue(String name) {
        return getValue(name, true);
    }


    public Object getValue(String name, boolean stripQuotes) {
        NameValue nv = this.getNameValue(name.toLowerCase());
        if (nv != null)
            return nv.getValueAsObject(stripQuotes);
        else
            return null;
    }

    public NameValue getNameValue(String name) {
        if(hmap == null) {
            return null;
        }
        return (NameValue) hmap.get(name.toLowerCase());
    }


    public boolean hasNameValue(String name) {
        return this.containsKey(name.toLowerCase());
    }


    public boolean delete(String name) {
        String lcName = name.toLowerCase();
        if (this.containsKey(lcName)) {
            this.remove(lcName);
            return true;
        } else {
            return false;
        }

    }

    public Object clone() {
        NameValueList retval = new NameValueList();
        retval.setSeparator(this.separator);
        if(hmap != null) {
            Iterator<NameValue> it = this.iterator();
            while (it.hasNext()) {
                retval.set((NameValue) ((NameValue) it.next()).clone());
            }
        }
        return retval;
    }


    public int size() {
        if(hmap == null) {
            return 0;
        }
        return hmap.size();
    }


    public boolean isEmpty() {
        if(hmap == null) {
            return true;
        }
        return hmap.isEmpty();
    }


    public Iterator<NameValue> iterator() {
        return this.getMap().values().iterator();
    }

    public Iterator<String> getNames() {
        return this.getMap().keySet().iterator();

    }

    public String getParameter(String name) {
        return getParameter(name, true);
    }


    public String getParameter(String name, boolean stripQuotes) {
        Object val = this.getValue(name, stripQuotes);
        if (val == null)
            return null;
        if (val instanceof GenericObject)
            return ((GenericObject) val).encode();
        else
            return val.toString();
    }


    public void clear() {
        if(hmap != null) {
            hmap.clear();
        }
    }


    public boolean containsKey(Object key) {
        if(hmap == null) {
            return false;
        }
        return hmap.containsKey(key.toString().toLowerCase());
    }


    public boolean containsValue(Object value) {
        if(hmap == null) {
            return false;
        }
        return hmap.containsValue(value);
    }


    public Set<Entry<String, NameValue>> entrySet() {
        if(hmap == null) {
            return new HashSet<Entry<String,NameValue>>();
        }
        return hmap.entrySet();
    }


    public NameValue get(Object key) {
        if(hmap == null) {
            return null;
        }
        return hmap.get(key.toString().toLowerCase());
    }


    public Set<String> keySet() {
        if(hmap == null) {
            return new HashSet<String>();
        }
        return hmap.keySet();
    }


    public NameValue put(String name, NameValue nameValue) {
        return this.getMap().put(name, nameValue);
    }

    public void putAll(Map<? extends String, ? extends NameValue> map) {
        this.getMap().putAll(map);
    }


    public NameValue remove(Object key) {
        if(hmap == null) {
            return null;
        }
        return this.getMap().remove(key.toString().toLowerCase());
    }

    public Collection<NameValue> values() {
        return this.getMap().values();
    }

    @Override
    public int hashCode() {
        return this.getMap().keySet().hashCode();
    }

    protected Map<String,NameValue> getMap() {
        if(this.hmap == null) {
            if (sync) {
                this.hmap = new ConcurrentHashMap<String,NameValue>(0);
            } else {
                this.hmap = new LinkedHashMap<String,NameValue>(0);
            }
        }
        return hmap;
    }
}
