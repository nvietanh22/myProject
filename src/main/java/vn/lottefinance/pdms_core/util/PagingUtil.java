package vn.lottefinance.pdms_core.util;

import org.springframework.data.domain.Page;

public class PagingUtil {
    public static <T> BasePagingResponse<T> fromPage(Page<T> page) {
        return BasePagingResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
