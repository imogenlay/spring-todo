package com.imogenlay.todo.task.dtos;

import java.util.List;
import com.imogenlay.todo.common.SortOrder;

public record TaskQueryParams(List<String> categories, SortOrder order) {
    
    public SortOrder orderOrDefault() {
        return order == null ? SortOrder.DESC : order;
    }
}
