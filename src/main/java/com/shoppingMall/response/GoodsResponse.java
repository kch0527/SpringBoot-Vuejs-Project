package com.shoppingMall.response;


import com.shoppingMall.entity.Goods;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsResponse {

    private final Long id;
    private final String title;
    private final String content;

    public GoodsResponse(Goods goods){
        this.id = goods.getId();
        this.title = goods.getTitle();
        this.content = goods.getContent();
    }

    @Builder
    public GoodsResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
