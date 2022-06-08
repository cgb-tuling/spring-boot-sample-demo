package org.example.validation.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 嵌套校验
 *
 * 比如，User信息的时候同时还带有Job信息。需要注意的是，此时DTO类的对应字段必须标记@Valid注解。
 */
@Data
public class UserDTO {  
  
    @Min(value = 10000000000000000L, groups = Update.class)
    private Long userId;  
  
    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 2, max = 10, groups = {Save.class, Update.class})
    private String userName;  
  
    @NotNull(groups = {Save.class, Update.class})  
    @Length(min = 6, max = 20, groups = {Save.class, Update.class})  
    private String account;  
  
    @NotNull(groups = {Save.class, Update.class})  
    @Length(min = 6, max = 20, groups = {Save.class, Update.class})  
    private String password;  
  
    @NotNull(groups = {Save.class, Update.class})  
    @Valid
    private Job job;  
  
    @Data  
    public static class Job {  
  
        @Min(value = 1, groups = Update.class)  
        private Long jobId;  
  
        @NotNull(groups = {Save.class, Update.class})  
        @Length(min = 2, max = 10, groups = {Save.class, Update.class})  
        private String jobName;  
  
        @NotNull(groups = {Save.class, Update.class})  
        @Length(min = 2, max = 10, groups = {Save.class, Update.class})  
        private String position;  
    }  
  
    public interface Save {  
    }  
  
    public interface Update {  
    }  
} 