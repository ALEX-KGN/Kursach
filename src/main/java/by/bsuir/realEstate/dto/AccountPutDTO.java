package by.bsuir.realEstate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountPutDTO {
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min=5, max = 30, message = "Размер email должен быть от 5 до 30")
    private String username;
    @NotEmpty(message = "Пароль не может быть пустым")
    private String oldPassword;
    private String newPassword;
    @Pattern(regexp = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})", message = "Номер телефона должен иметь вид +375*********")
    private String phoneNumber;

    public AccountPutDTO() {
    }

    public AccountPutDTO(String username, String oldPassword, String newPassword, String phoneNumber) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public AccountPutDTO(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}


