package br.com.claro.BoxTV.DTO;

public class StatusDTO {

    private boolean status;
    private String message;

    public StatusDTO() {}

    public StatusDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
