package com.example.data.service;

import java.util.List;

public interface DataTransfer {

    boolean doImport(List<Object[]> values);

    default void doClean(){}
}
