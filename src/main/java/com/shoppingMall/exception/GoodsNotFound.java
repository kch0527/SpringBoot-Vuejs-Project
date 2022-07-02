package com.shoppingMall.exception;

public class GoodsNotFound extends TopException{

    private static final String MESSAGE = "존재하지 않는 상품";

    public GoodsNotFound() {
        super(MESSAGE);
    }


    @Override
    public int getStatusCode() {
        return 404;
    }
}
