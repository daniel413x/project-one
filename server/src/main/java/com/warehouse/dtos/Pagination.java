package com.warehouse.dtos;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    public Pagination(Pageable pageRequest, Long count) {
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        int pages = (int) Math.ceil((double) count / size);
        boolean pageLimitReached = page >= pages - 1;
        // return the page number without zero-based pagination for client app ux
        page = page + 1;
        this.page = page;
        this.pages = pages;
        this.count = count;
        this.size = size;
        this.pageLimitReached = pageLimitReached;
    }

    private Integer page;

    private Integer size;

    private Integer pages;

    private Long count;

    private Boolean pageLimitReached;
};