package com.shoppingMall.repository;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.request.GoodsSearch;

import java.util.List;

public interface GoodsRepositoryCustom {

    List<Goods> getList(GoodsSearch goodsSearch);
}
