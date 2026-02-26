package com.imogenlay.todo.category.dtos;

import com.imogenlay.todo.common.SortOrder;

public record CategoryQueryParams(SortOrder order) {
    
    public SortOrder orderOrDefault() {
        return order == null ? SortOrder.DESC : order;
    }
}
