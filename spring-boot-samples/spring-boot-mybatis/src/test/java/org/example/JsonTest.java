package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Phone;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yct
 */
public class JsonTest {
    private ObjectMapper mapper=new ObjectMapper();
    @Test
    public void testJson6() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "abdae");
        map.put("name", "我的");
        map.put("model", "nokia");
        map.put("size", "6.0");
        String s = mapper.writeValueAsString(map);
        Phone phone = mapper.readValue(s, Phone.class);
        System.out.println(phone);
        //Phone{id='abdae', model='nokia', other={size=6.0, name=我的}}
    }
}
