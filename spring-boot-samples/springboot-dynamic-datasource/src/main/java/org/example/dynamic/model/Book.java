package org.example.dynamic.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class Book extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String bookName;

    private String author;

    private String description;

    private BigDecimal price;

    private Integer stock;


}