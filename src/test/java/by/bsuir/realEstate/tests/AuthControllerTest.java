package by.bsuir.realEstate.tests;

import by.bsuir.realEstate.dto.AccountDTO;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.repositories.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AuthControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        accountRepository.deleteAll();
    }

    @Test
    public void handlePostCreatingANewAccountWithAnExistingUsername_ReturnsConflictStatus() throws Exception {
        createAccount("vova@mail.ru", "1234", "ROLE_ADMIN", "+375292903121");
        AccountDTO accountDTO = new AccountDTO("vova@mail.ru", "1234");
        accountDTO.setPhoneNumber("+375292903121");
        mockMvc.perform(
                        post("/api/auth/registration")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void handlePostLogin_ReturnsOkStatus() throws Exception {
        createAccount("vova@mail.ru", "1234", "ROLE_ADMIN", "+375292903121");
        AccountDTO accountDTO = new AccountDTO("vova@mail.ru", "1234");
        accountDTO.setPhoneNumber("+375292903121");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void handlePostCreatingANewAccount_ReturnsOkStatus() throws Exception{
        AccountDTO accountDTO = new AccountDTO("vova@mail.ru", "1234");
        accountDTO.setPhoneNumber("+375292903121");
        mockMvc.perform(
                        post("/api/auth/registration")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void handlePostCreatingANewAccountWithWrongPhoneNumber_Returns4xxStatus() throws Exception{
        AccountDTO accountDTO = new AccountDTO("vova@mail.ru", "1234");
        accountDTO.setPhoneNumber("+3752929031213213");
        mockMvc.perform(
                        post("/api/auth/registration")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void handlePostLoginAccount_ReturnsOkStatus() throws Exception{
        createAccount("vova@mail.ru", "1234", "ROLE_ADMIN", "+3752921222122");
        AccountDTO accountDTO = new AccountDTO("vova@mail.ru", "1234");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    Account createAccount(String username, String password, String role, String phoneNumber){
        Account account = new Account(username, passwordEncoder.encode(password), role, phoneNumber);
        return accountRepository.save(account);
    }

}