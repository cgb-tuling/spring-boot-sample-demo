package org.example.domain;
 
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
 
import java.util.HashMap;
import java.util.Map;
 
public class Phone {
    private String id;
    private String model;
    private Map<String,Object> other = new HashMap<>();
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getModel() {
        return model;
    }
 
    public void setModel(String model) {
        this.model = model;
    }
 
    @JsonAnyGetter
    public Map<String, Object> getOther() {
        return other;
    }
 
    /**
     * 没有匹配上的反序列化属性，放到这里
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void setOther(String key, Object value) {
        this.other.put(key,value);
    }
 
    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", other=" + other +
                '}';
    }
}