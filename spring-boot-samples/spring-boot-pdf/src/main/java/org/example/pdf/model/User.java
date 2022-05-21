package org.example.pdf.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: 用户
 * User: yuanct
 * Date: 2019/1/9 2:21 PM
 */
@Data
@AllArgsConstructor
public class User {

    private Long id;
    private Integer age;
    private String name;

}
