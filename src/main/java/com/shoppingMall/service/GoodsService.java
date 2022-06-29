package com.shoppingMall.service;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public void saveGoods(GoodsCreate goodsCreate){
        Goods goods = Goods.builder()
                .title(goodsCreate.getTitle())
                .content(goodsCreate.getContent())
                .build();
        goodsRepository.save(goods);
    }

    public Goods getGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품"));

        return goods;
    }
}
