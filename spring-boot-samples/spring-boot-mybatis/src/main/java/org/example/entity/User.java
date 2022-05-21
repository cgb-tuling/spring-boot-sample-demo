package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.EducationEnum;
import org.example.enums.SexEnum;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Long id;

    private String userName;

    private SexEnum sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    private EducationEnum education;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public User(String userName, SexEnum sex, Date birthDay, EducationEnum education, Date createTime, Date updateTime) {
        this.userName = userName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.education = education;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
