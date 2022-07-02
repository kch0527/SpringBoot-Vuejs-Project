package com.shoppingMall.exception;

public class GoodsNotFound extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 상품";

    public GoodsNotFound() {
        super(MESSAGE);
    }

}
