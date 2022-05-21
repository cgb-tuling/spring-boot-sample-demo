package org.example.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dell
 * @date 2020/9/8 14:55
 * @Description
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {

     private Set<Integer> set = new HashSet<>();
    /**
     *  初始化方法
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
         // 说明给定的值  @ListValue(vals={0,1},groups = {AddGroup.class}) vals声明的值
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
        //声明的值存入set
            set.add(val);
        }
    }
    /**
     *  判断是否校验成功
     *  value:提交过来需要校验的值（传的参）
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
         //判断提交的值是否在set中
        return set.contains(value);
    }
}

