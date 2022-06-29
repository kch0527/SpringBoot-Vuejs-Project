package com.shoppingMall.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsResponse {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public GoodsResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
