package com.shoppingMall.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class GoodsCreate {

    @NotBlank(message = "타이틀이 없음")
    private String title;
    @NotBlank(message = "컨텐츠가 없음")
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Builder
    public GoodsCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
