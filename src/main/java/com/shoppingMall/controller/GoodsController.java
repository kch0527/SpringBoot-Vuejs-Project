package com.shoppingMall.controller;

import com.shoppingMall.request.GoodsCreate;
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
public class GoodsController {

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid GoodsCreate goodsCreate, BindingResult result){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, defaultMessage);
            return error;
        }

        return Map.of();
    }
}
