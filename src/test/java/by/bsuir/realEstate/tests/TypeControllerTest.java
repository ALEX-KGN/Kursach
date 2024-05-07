package by.bsuir.realEstate.tests;

import by.bsuir.realEstate.dto.AccountDTO;
import by.bsuir.realEstate.dto.TypeDTO;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.models.Type;
import by.bsuir.realEstate.repositories.AccountRepository;
import by.bsuir.realEstate.repositories.TypeRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class TypeControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        typeRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void handlePostTypeWithAccess_ReturnsOkStatus() throws Exception {
        Type type = new Type("Хрущевка");
        createAccount("admin@mail.ru", "1234", "ROLE_ADMIN", "+3752912312123");
        AccountDTO accountDTO = new AccountDTO("admin@mail.ru", "1234");
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login").content(objectMapper.writeValueAsString(accountDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        String jwtToken = objectMapper.readTree(json).get("jwtToken").asText();
        mockMvc.perform(
                        post("/api/types")
                                .content(objectMapper.writeValueAsString(type))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer "  + jwtToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void handlePostWrongTypeWithAccess_Returns4xxStatus() throws Exception {
        Type type = new Type("Хрущевка");
        typeRepository.save(type);
        TypeDTO typeDTO = new TypeDTO("Хрущевка");
        createAccount("admin@mail.ru", "1234", "ROLE_ADMIN", "+3752912312123");
        AccountDTO accountDTO = new AccountDTO("admin@mail.ru", "1234");
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login").content(objectMapper.writeValueAsString(accountDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        String jwtToken = objectMapper.readTree(json).get("jwtToken").asText();
        mockMvc.perform(
                        post("/api/types")
                                .content(objectMapper.writeValueAsString(typeDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer "  + jwtToken)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void handlePostTypeWithoutAccess_ReturnsForbiddenStatus() throws Exception {
        Type type = new Type("Домик");
        mockMvc.perform(
                        post("/api/types")
                                .content(objectMapper.writeValueAsString(type))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void handleGetTypes_ReturnsOkStatus() throws Exception {
        Type type = new Type("Домик");
        typeRepository.save(type);
        mockMvc.perform(
                        get("/api/types")).andExpectAll( status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "name": "Домик"
                                    }
                                ]
                                """));
    }


    Account createAccount(String username, String password, String role, String phoneNumber){
        Account account = new Account(username, passwordEncoder.encode(password), role, phoneNumber);
        return accountRepository.save(account);
    }


}