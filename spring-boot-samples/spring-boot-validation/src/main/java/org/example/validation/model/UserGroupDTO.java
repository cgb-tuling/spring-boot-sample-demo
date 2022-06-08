package org.example.validation.model;

import lombok.Data;
import org.example.validation.group.ValidGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分组校验
 */
@Data
public class UserGroupDTO {
  
    @Min(value = 10000000000000000L, groups = ValidGroup.Crud.Update.class)
    private Long userId;  
  
    @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    @Length(min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String userName;  
  
    @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
    @Length(min = 6, max = 20, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
    private String account;  
  
    @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
    @Length(min = 6, max = 20, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
    private String password;  
  
    @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
    @Valid
    private Job job;  
  
    @Data  
    public static class Job {  
  
        @Min(value = 1, groups = ValidGroup.Crud.Update.class)  
        private Long jobId;  
  
        @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
        @Length(min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
        private String jobName;  
  
        @NotNull(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
        @Length(min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})  
        private String position;  
    }
} 