package Likelion.Recruiting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {
    private int totalPage;
    private Long totalElements;
    private int pagingSize;
    private int currentPage;
    private Boolean isFirst;
    private Boolean isLast;
    private Boolean isEmpty;
    private List<T> data;


    public PageResponseDto(Page<T> page) {
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pagingSize = page.getSize();
        this.currentPage = page.getNumber() + 1;
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.isEmpty = page.isEmpty();
        this.data = page.getContent();
    }
}
