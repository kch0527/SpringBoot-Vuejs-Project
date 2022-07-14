package com.shoppingMall.service;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.exception.GoodsNotFound;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import com.shoppingMall.request.GoodsEdit;
import com.shoppingMall.request.GoodsSearch;
import com.shoppingMall.response.GoodsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GoodsServiceTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    @BeforeEach
    void clear() {
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    void test1() {
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
    void test2() {
        //given
        Goods requestGoods = Goods.builder()
                .title("자켓")
                .content("따뜻함")
                .build();
        goodsRepository.save(requestGoods);

        //when
        GoodsResponse goods = goodsService.getGoods(requestGoods.getId());

        //then
        Assertions.assertNotNull(goods);
        assertEquals("자켓", goods.getTitle());
        assertEquals("따뜻함", goods.getContent());
    }


    @Test
    @DisplayName("1페이지 조회")
    void test3() {
        //given

        List<Goods> goodsList = IntStream.range(0, 30)
                .mapToObj(i -> {
                    return Goods.builder()
                            .title("상품이름 : " + i)
                            .content("상품설명 : " + i)
                            .build();
                })
                .collect(Collectors.toList());
        goodsRepository.saveAll(goodsList);

        GoodsSearch goodsSearch = GoodsSearch.builder()
                .page(1)
                .build();

        //when
        List<GoodsResponse> list = goodsService.getList(goodsSearch);

        //then
        assertEquals(10L, list.size());
        assertEquals("상품이름 : 29", list.get(0).getTitle());
    }

    @Test
    @DisplayName("상품명 수정")
    void test4() {
        //given
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        GoodsEdit goodsEdit = GoodsEdit.builder()
                .title("긴팔")
                .content("쉬원함")
                .build();

        //when
        goodsService.edit(goods.getId(), goodsEdit);

        //then
        Goods updateGoods = goodsRepository.findById(goods.getId()).orElseThrow(() -> new RuntimeException("상품이 없음 id : " + goods.getId()));

        Assertions.assertEquals("긴팔", updateGoods.getTitle());

    }

    @Test
    @DisplayName("상품내용 수정")
    void test5() {
        //given
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        GoodsEdit goodsEdit = GoodsEdit.builder()
                .title("반팔")
                .content("더움")
                .build();

        //when
        goodsService.edit(goods.getId(), goodsEdit);

        //then
        Goods updateGoods = goodsRepository.findById(goods.getId()).orElseThrow(() -> new RuntimeException("상품이 없음 id : " + goods.getId()));

        Assertions.assertEquals("더움", updateGoods.getContent());
    }

    @Test
    @DisplayName("상품 삭제 기능")
    void test6() {
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        goodsService.delete(goods.getId());

        Assertions.assertEquals(0, goodsRepository.count());

    }


    @Test
    @DisplayName("단일 조회 예외처리")
    void test7() {
        //given
        Goods requestGoods = Goods.builder()
                .title("자켓")
                .content("따뜻함")
                .build();
        goodsRepository.save(requestGoods);

        //expected
        assertThrows(GoodsNotFound.class, () -> {
            goodsService.getGoods(requestGoods.getId() + 1L);
        });

    }

    @Test
    @DisplayName("상품 삭제 예외처리")
    void test8() {
        //given
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        //expected
        assertThrows(GoodsNotFound.class, () -> {
            goodsService.delete(goods.getId() + 1L);
        });
    }

    @Test
    @DisplayName("상품 수정 예외처리")
    void test9() {
        //given
        Goods goods = Goods.builder()
                .title("반팔")
                .content("쉬원함")
                .build();
        goodsRepository.save(goods);

        GoodsEdit goodsEdit = GoodsEdit.builder()
                .title("반팔")
                .content("더움")
                .build();

        //expected
        assertThrows(GoodsNotFound.class, () -> {
            goodsService.edit(goods.getId() + 1L, goodsEdit);
        });
    }
}