package com.shoppingMall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.chan.com", uriPort = 443)
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class GoodsControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    @DisplayName("글 단건 조회 테스트 문서 생성")
    void test1() throws Exception {

        Goods requestGoods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(requestGoods);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/goods/{goodsId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("goods-inquiry", RequestDocumentation
                        .pathParameters(
                                RequestDocumentation.parameterWithName("goodsId").description("상품 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("상품 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("상품 이름"),
                                PayloadDocumentation.fieldWithPath("content").description("상품 소개")
                                )
                ));
    }


    @Test
    @DisplayName("글 등록 테스트 문서 생성")
    void test2() throws Exception {

        GoodsCreate request = GoodsCreate.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("goods-create", PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("title").description("상품 이름")
                                .attributes(Attributes.key("constraint").value("정확한 상품명 입력")),
                        PayloadDocumentation.fieldWithPath("content").description("상품 소개").optional()
                        )
                ));
    }
}
