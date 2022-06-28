package com.shoppingMall.service;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public void saveGoods(GoodsCreate goodsCreate){
        Goods goods = new Goods(goodsCreate.getTitle(), goodsCreate.getContent());
        goodsRepository.save(goods);
    }
}
