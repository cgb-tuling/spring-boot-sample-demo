package org.example.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Dell
 * @date 2020/9/8 14:44
 * @Description
 */

@Documented
//指定校验器
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    //错误提示值一般为校验注解类的全类名.message    ListValue 的全类名 
    String message() default "{com.dell.common.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
     //定义注解@ListValue(vals={0,1},groups = {AddGroup.class})  vals值默认为空数组
    int [] vals () default { };
}

