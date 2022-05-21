package org.example.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yct
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -3304666336015747L;
    private String id;
    private String name;
}