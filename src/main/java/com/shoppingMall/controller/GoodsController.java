package com.shoppingMall.controller;

import com.shoppingMall.request.GoodsCreate;
import com.shoppingMall.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid GoodsCreate request){
        goodsService.saveGoods(request);
        return Map.of();
    }
}
