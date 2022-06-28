package com.shoppingMall.controller;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoodsRepository goodsRepository;

    @BeforeEach
    void clear(){
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청")
    void test() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"상품명 입니다.\", \"content\": \"상품소개 입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청"))
                .andExpect(jsonPath("$.validation.title").value("타이틀이 없음"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 db에 값 저장")
    void testSaveGoods() throws Exception {
        //when 이러한 요청을 했을 때
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"상품명 입니다.\", \"content\": \"상품소개 입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then 이러한 결과가 나온다.
        assertEquals(1L, goodsRepository.count());
        Goods goods = goodsRepository.findAll().get(0);
        assertEquals("상품명 입니다.", goods.getTitle());
        assertEquals("상품소개 입니다.", goods.getContent());

    }
}
