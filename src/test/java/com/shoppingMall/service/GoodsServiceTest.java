package com.shoppingMall.service;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoodsServiceTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    @BeforeEach
    void clear(){
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    void test1(){
        //given
        GoodsCreate goodsCreate = GoodsCreate.builder()
                .title("상품명 입니다.")
                .content("상품소개 입니다.")
                .build();
        //when
        goodsService.saveGoods(goodsCreate);

        //then
        assertEquals(1L, goodsRepository.count());
        Goods goods = goodsRepository.findAll().get(0);
        assertEquals("상품명 입니다.", goods.getTitle());
        assertEquals("상품소개 입니다.", goods.getContent());
    }

    @Test
    @DisplayName("단일 조회")
    void test2(){
        //given
        Goods requestGoods = Goods.builder()
                .title("자켓")
                .content("따뜻함")
                .build();
        goodsRepository.save(requestGoods);

        //when
        Goods goods = goodsService.getGoods(requestGoods.getId());

        //then
        Assertions.assertNotNull(goods);
        assertEquals("자켓", goods.getTitle());
        assertEquals("따뜻함", goods.getContent());
    }

}