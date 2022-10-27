package org.jredfoot.utgrid.common.ws.cfx;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class StringObjectMapAdapter extends XmlAdapter<StringObjectMap, Map<String, Object>> {
    public StringObjectMap marshal(Map<String, Object> v) throws Exception {
        StringObjectMap map = new StringObjectMap();
        for (Map.Entry<String, Object> e : v.entrySet()) { 
            StringObjectMap.StringObjectEntry iue = new StringObjectMap.StringObjectEntry();
            iue.setObject(e.getValue());
            iue.setId(e.getKey());
            map.getEntries().add(iue);
        }
        return map;
    }

    public Map<String, Object> unmarshal(StringObjectMap v) throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (StringObjectMap.StringObjectEntry e : v.getEntries()) {
            map.put(e.getId(), e.getObject());
        }
        return map;
    }

}

