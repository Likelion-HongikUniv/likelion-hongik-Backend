package Likelion.Recruiting.exception.community;

import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.model.dto.ErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CommunityErrorDto {
    private int code;
    private String message;

    @Builder
    public CommunityErrorDto(CommunityException communityException) {
        this.code = communityException.getCode();
        this.message = communityException.getMessage();
    }
}
