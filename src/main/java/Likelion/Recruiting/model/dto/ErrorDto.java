package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Builder
@NoArgsConstructor
public class ErrorDto {

    private int code;
    private String message;

    public ErrorDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Map convertToJson(int code, String message) {
        Map classMap = new HashMap();

        classMap.put("code", code);
        classMap.put("message", message);

        return classMap;
    }
    public String convertObjectToJsonString(ErrorDto errorCode) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(errorCode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
