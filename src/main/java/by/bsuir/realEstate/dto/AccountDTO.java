package by.bsuir.realEstate.dto; //приём отправка данных

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountDTO {
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min=5, max = 30, message = "Размер email должен быть от 5 до 30")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    private String role;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})", message = "Номер телефона должен иметь вид +375*********")
    private String phoneNumber;

    public AccountDTO(String username, String password, String role, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public AccountDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AccountDTO() {
    }
}

