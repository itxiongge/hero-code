package com.hero.service;

import java.util.concurrent.CompletableFuture;

public interface OtherService {
    String doFirst();
    String doSecond();

    CompletableFuture<String> doThird();
    CompletableFuture<String> doFourth();
}
