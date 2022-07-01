package com.shoppingMall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsEditor {

    private String title;
    private String content;

    @Builder
    public GoodsEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
