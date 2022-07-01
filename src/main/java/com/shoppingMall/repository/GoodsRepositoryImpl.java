package com.shoppingMall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingMall.entity.Goods;
import com.shoppingMall.entity.QGoods;
import com.shoppingMall.request.GoodsSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.shoppingMall.entity.QGoods.goods;

@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Goods> getList(GoodsSearch goodsSearch) {
        return jpaQueryFactory.selectFrom(goods)
                .limit(goodsSearch.getSize())
                .offset(goodsSearch.getOffset())
                .orderBy(goods.id.desc())
                .fetch();
    }
}
