package com.forget1026.practice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class PracticeRestControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("validation 테스트")
    public void validationCheckTest() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("query", "테스트");
        param.add("size", "-1");

        mvc.perform(get("/search")
                        .params(param))
                .andExpect(status().is5xxServerError())
                .andDo(print());

        param.clear();
        param.add("query", "테스트");
        param.add("page", "-1");

        mvc.perform(get("/search")
                        .params(param))
                .andExpect(status().is5xxServerError())
                .andDo(print());

        param.clear();
        param.add("query", "");

        mvc.perform(get("/search")
                        .params(param))
                .andExpect(status().is5xxServerError())
                .andDo(print());

        param.clear();
        param.add("query", "테스트");
        mvc.perform(get("/search")
                        .params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카운팅 테스트")
    public void countCheckTest() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("query", "테스트");

        for (int i = 0; i < 10; i++) {
            mvc.perform(get("/search")
                            .params(param))
                    .andExpect(status().isOk());
        }
        param.clear();
        param.add("query", "이효리");
        for (int i = 0; i < 5; i++) {
            mvc.perform(get("/search")
                            .params(param))
                    .andExpect(status().isOk());
        }

        mvc.perform(get("/top10"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}