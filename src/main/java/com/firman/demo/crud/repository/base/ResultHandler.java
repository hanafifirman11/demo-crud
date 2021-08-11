package com.firman.demo.crud.repository.base;

import java.util.List;

public interface ResultHandler {

    @SuppressWarnings("rawtypes")
    List getResult(List<Object[]> result);

}
