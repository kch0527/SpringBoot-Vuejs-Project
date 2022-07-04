package com.shoppingMall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import com.shoppingMall.request.GoodsEdit;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoodsRepository goodsRepository;

    @BeforeEach
    void clear(){
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청")
    void test() throws Exception {

        GoodsCreate request = GoodsCreate.builder()
                .title("상품명 입니다.")
                .content("상품소개 입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println("------------------------");
        System.out.println(json);
        System.out.println("------------------------");

        mockMvc.perform(post("/goods")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {
        GoodsCreate request = GoodsCreate.builder()
                .content("상품소개 입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/goods")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                //.andExpect(jsonPath("$.code").value("400"))
                //.andExpect(jsonPath("$.message").value("잘못된 요청"))
                //.andExpect(jsonPath("$.validation.title").value("타이틀이 없음"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 db에 값 저장")
    void test3() throws Exception {

        GoodsCreate request = GoodsCreate.builder()
                .title("상품명 입니다.")
                .content("상품소개 입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        //when 이러한 요청을 했을 때
        mockMvc.perform(post("/goods")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then 이러한 결과가 나온다.
        assertEquals(1L, goodsRepository.count());
        Goods goods = goodsRepository.findAll().get(0);
        assertEquals("상품명 입니다.", goods.getTitle());
        assertEquals("상품소개 입니다.", goods.getContent());

    }

    @Test
    @DisplayName("단일 조회")
    void test4() throws Exception {
        Goods requestGoods = Goods.builder()
                .title("012345678901234")
                .content("따뜻함")
                .build();
        goodsRepository.save(requestGoods);

        mockMvc.perform(get("/goods/{goodsId}", requestGoods.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestGoods.getId()))
                .andExpect(jsonPath("$.title").value("0123456789"))
                .andExpect(jsonPath("$.content").value("따뜻함"))
                .andDo(print());
    }

    @Test
    @DisplayName("목록 조회")
    void test5() throws Exception {

        List<Goods> goodsList = IntStream.range(0, 30)
                .mapToObj(i -> {
                    return Goods.builder()
                            .title("상품이름 : " + i)
                            .content("상품설명 : " + i)
                            .build();
                })
                .collect(Collectors.toList());
        goodsRepository.saveAll(goodsList);

        mockMvc.perform(get("/goods?page=1&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$.[0].title").value("상품이름 : 29"))
                .andExpect(jsonPath("$.[0].content").value("상품설명 : 29"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지가 0이면 1로 가져옴")
    void test6() throws Exception {

        List<Goods> goodsList = IntStream.range(0, 30)
                .mapToObj(i -> {
                    return Goods.builder()
                            .title("상품이름 : " + i)
                            .content("상품설명 : " + i)
                            .build();
                })
                .collect(Collectors.toList());
        goodsRepository.saveAll(goodsList);

        mockMvc.perform(get("/goods?page=0&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$.[0].title").value("상품이름 : 29"))
                .andExpect(jsonPath("$.[0].content").value("상품설명 : 29"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정")
    void test7() throws Exception {

        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        GoodsEdit goodsEdit = GoodsEdit.builder()
                .title("긴팔")
                .content("쉬원함")
                .build();

        mockMvc.perform(patch("/goods/{goodsId}", goods.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodsEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("삭제 기능")
    void test8() throws Exception {
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        mockMvc.perform(delete("/goods/{goodsId}",goods.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("상품 조회 예외처리")
    void test9() throws Exception{

        mockMvc.perform(get("/goods/{goodsId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 예외처리")
    void test10() throws Exception{

        GoodsEdit goodsEdit = GoodsEdit.builder()
                .title("긴팔")
                .content("쉬원함")
                .build();

        mockMvc.perform(patch("/goods/{goodsId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodsEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 등록시 상품명 특정언어 제한")
    void test11() throws Exception{

        GoodsCreate request = GoodsCreate.builder()
                .title("바보입니다.")
                .content("상품소개 입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/goods")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }
}
