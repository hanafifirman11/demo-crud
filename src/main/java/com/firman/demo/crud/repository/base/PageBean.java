package com.firman.demo.crud.repository.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PageBean<T> {

    private Integer number = 0;
    private Integer totalPages = 0;
    private Long totalElements = -1L;
    private Integer size = 20;
    private String ql;
    private String countQl;
    private List<T> content = new ArrayList<T>();

    public void init() {
        if (number == null || number < 0) {
            number = 0;
        }

        if (totalPages == null || totalPages < 0) {
            totalPages = 0;
        }

        if (totalElements == null) {
            totalElements = -1L;
        }

        if (size == null || size < 0) {
            size = 20;
        }
    }

}
