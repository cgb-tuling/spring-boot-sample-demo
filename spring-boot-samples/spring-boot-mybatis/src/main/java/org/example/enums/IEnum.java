package org.example.enums;

public interface IEnum<E extends Enum<?>, T> {

    T getValue();

}
