package com.shoppingMall.entity;

import com.shoppingMall.request.GoodsEdit;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Goods(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public GoodsEditor.GoodsEditorBuilder toEditor(){
        return GoodsEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(GoodsEditor goodsEditor) {
        title = goodsEditor.getTitle();
        content = goodsEditor.getContent();
    }
}
