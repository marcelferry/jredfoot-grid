package org.jredfoot.utgrid.common.ws.cfx;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "StringObjectMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class StringObjectMap {
    @XmlElement(nillable = false, name = "entry")
    List<StringObjectEntry> entries = new ArrayList<StringObjectEntry>();

    public List<StringObjectEntry> getEntries() {
        return entries;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "IdentifiedData")
    static class StringObjectEntry {
        @XmlElement(required = true, nillable = false)
        String id;

        Object key;

        public void setId(String k) {
            id = k;
        }
        public String getId() {
            return id;
        }

        public void setObject(Object u) {
            key = u;
        }
        public Object getObject() {
            return key;
        }
    }
}

