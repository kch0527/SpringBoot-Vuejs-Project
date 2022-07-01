package com.shoppingMall.service;

import com.shoppingMall.entity.Goods;
import com.shoppingMall.entity.GoodsEditor;
import com.shoppingMall.repository.GoodsRepository;
import com.shoppingMall.request.GoodsCreate;
import com.shoppingMall.request.GoodsEdit;
import com.shoppingMall.request.GoodsSearch;
import com.shoppingMall.response.GoodsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public void saveGoods(GoodsCreate goodsCreate){
        Goods goods = Goods.builder()
                .title(goodsCreate.getTitle())
                .content(goodsCreate.getContent())
                .build();
        goodsRepository.save(goods);
    }

    public GoodsResponse getGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품"));

        GoodsResponse goodsResponse = GoodsResponse.builder()
                .id(goods.getId())
                .title(goods.getTitle())
                .content(goods.getContent())
                .build();

        return goodsResponse;
    }

    public List<GoodsResponse> getList(GoodsSearch goodsSearch) {
        //Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC ,"id"));
        return goodsRepository.getList(goodsSearch).stream()
                .map(GoodsResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, GoodsEdit goodsEdit){
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품"));

        GoodsEditor.GoodsEditorBuilder goodsEditorBuilder = goods.toEditor();

        GoodsEditor goodsEditor = goodsEditorBuilder.title(goodsEdit.getTitle())
                .content(goodsEdit.getContent())
                .build();

        goods.edit(goodsEditor);
    }

    public void delete(Long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품"));
        goodsRepository.delete(goods);
    }
}
