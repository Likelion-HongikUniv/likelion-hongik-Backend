package Likelion.Recruiting.model.dto;

import lombok.Data;

@Data
public class CreateResponseMessage {
    private Long responseCode;
    private String message;

    public CreateResponseMessage(Long responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
