package com.codesoom.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ToyStoreServiceTest {

    @Autowired
    ToyStoreService toyStoreService;

    @DisplayName("`이름, 메이커, 가격, 이미지 URL`을 입력 받아 장난감 등록")
    @Test
    void addToyTest() {
        Product product = toyStoreService.save("name", "maker", 5000, "abc.jpg");

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getMaker()).isEqualTo("maker");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isEqualTo("abc.jpg");
    }

}