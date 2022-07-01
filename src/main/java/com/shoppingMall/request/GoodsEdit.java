package com.shoppingMall.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
public class GoodsEdit {

    @NotBlank(message = "타이틀이 없음")
    private String title;

    @NotBlank(message = "컨텐츠가 없음")
    private String content;

    public GoodsEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
