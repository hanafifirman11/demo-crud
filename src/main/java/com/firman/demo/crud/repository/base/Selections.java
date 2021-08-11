package com.firman.demo.crud.repository.base;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

@FunctionalInterface
public interface Selections<T> {

    List<Selection<?>> toSelection(Root<T> root);

}
