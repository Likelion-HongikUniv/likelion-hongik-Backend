package Likelion.Recruiting.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResponseDto<T> {
    private int count;
    private T data;
}