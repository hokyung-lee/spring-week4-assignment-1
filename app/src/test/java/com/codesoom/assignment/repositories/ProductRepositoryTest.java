package com.codesoom.assignment.repositories;

import com.codesoom.assignment.contexts.ContextProduct;
import com.codesoom.assignment.domains.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("ProductRepositoryTest 의")
class ProductRepositoryTest extends ContextProduct {

    @Autowired
    private ProductRepository productRepository;

    private final Product catTower = generateCatTower();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }


    @Nested
    @DisplayName("findAll() 매소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("고양이 장난감 물품이 존재하지 않을 때")
        class Context_empty_product {

            @Test
            @DisplayName("사이즈가 0인 빈 물품 리스트를 반환한다.")
            void it_returns_empty_list() {
                List<Product> results = productRepository.findAll();

                assertThat(results).hasSize(0);
            }
        }


        @Nested
        @DisplayName("고양이 장난감 물품이 1개라도 존재할 때")
        class Context_exist_product {

            private Product existed;

            @BeforeEach
            void setUp() {
                this.existed = productRepository.save(catTower);
            }

            @Test
            @DisplayName("사이즈가 0인 빈 물품 리스트를 반환한다.")
            void it_returns_empty_list() {
                List<Product> results = productRepository.findAll();

                assertThat(results).hasSize(1);
                assertThat(results.get(0).getProductId()).isEqualTo(existed.getProductId());
            }
        }
    }

    @Nested
    @DisplayName("findById() 매소드는")
    class Describe_findById {

        @Nested
        @DisplayName("id에 해당하는 고양이 물품이 존재할 때")
        class Context_exist_product {

            private Long productId;

            @BeforeEach
            void setUp() {
                this.productId = productRepository.save(catTower).getProductId();
            }

            @Test
            @DisplayName("id와 일치하는 고양이 물품을 반환한다. ")
            void it_returns_empty_list() {
                Optional<Product> found = productRepository.findById(productId);

                assertThat(found).isNotNull();
                assertThat(found.get().getProductId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 고양이 물품이 존재하지 않을때")
        class Context_non_exist_product {

            private Long productId;

            @BeforeEach
            void setUp () {
                this.productId = productRepository.save(catTower).getProductId();
                productRepository.deleteById(productId);
            }

            @Test
            @DisplayName("Null을 반환한다.")
            void it_returns_empty_list() {
                Optional<Product> found = productRepository.findById(productId);

                assertThat(found).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("save() 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("올바른 형식의 Product 의 생성요청이 들어올때")
        class Context_validFormatProduct {

            private final Product inputProduct = catTower;

            @Test
            @DisplayName("신규 Product 를 생성한 뒤 반환한다.")
            void it_returns_created_product() {
                Product created = productRepository.save(inputProduct);

                assertThat(created).isNotNull();
                assertThat(created).isEqualTo(inputProduct);
            }
        }
    }

    @Nested
    @DisplayName("deleteById() 메소드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("특정 id에 해당하는 Product 가 존재할 때")
        class Context_existMatchedProduct {

            private Long productId;

            @BeforeEach
            void setUp() {
                this.productId = productRepository.save(catTower).getProductId();
            }

            @Test
            @DisplayName("해당 Product 를 삭제한다.")
            void it_deletes_matched_product() {
                productRepository.deleteById(productId);

                Optional<Product> found = productRepository.findById(productId);
                assertThat(found).isEmpty();
            }
        }
    }

}
