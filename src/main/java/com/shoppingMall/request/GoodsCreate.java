package com.shoppingMall.request;

import com.shoppingMall.exception.InvalidRequest;
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


    @Builder
    public GoodsCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate(){
        if (title.contains("바보")){
            throw new InvalidRequest("title", "상품명에 바보를 포함할 수 없습니다.");
        }
    }
}
