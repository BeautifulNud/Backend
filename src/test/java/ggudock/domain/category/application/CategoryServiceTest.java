package ggudock.domain.category.application;

import ggudock.domain.category.application.dto.CategoryDto;
import ggudock.domain.category.entity.Category;
import ggudock.global.exception.constant.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    Category category1;
    Category category2;

    @BeforeEach
    public void setUp() {

        //given
        category1 = Category.builder()
                .name("초밥")
                .icon("/src/adadasd")
                .build();
        category2 = Category.builder()
                .name("김밥")
                .icon("/src/adadasd")
                .build();
        //when
        categoryService.save(category1);
        categoryService.save(category2);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void delete() {
        //given

        //when
        ResponseEntity<?> responseEntity = categoryService.delete(category1.getId());

        //then
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("카테고리 삭제 - 카테고리 못 찾을때")
    void deleteNotFound() {
        //given

        //when
        ResponseEntity<?> responseEntity = categoryService.delete(0L);

        //then
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals(ErrorCode.NOT_FOUND_CATEGORY,responseEntity.getBody());
    }

    @Test
    @DisplayName("카테고리 하나 검색")
    void getCategory() {
        //given
        CategoryDto categoryDto = CategoryDto.builder()
                .name(category2.getName())
                .icon(category2.getIcon())
                .build();
        //when
        ResponseEntity<?> responseEntity = categoryService.getCategory(category2.getId());

        //then
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(categoryDto,responseEntity.getBody());
    }

    @Test
    @DisplayName("카테고리 삭제 - 카테고리 못 찾을때")
    void getCategoryNotFound() {
        //given

        //when
        ResponseEntity<?> responseEntity = categoryService.getCategory(0L);

        //then
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals(ErrorCode.NOT_FOUND_CATEGORY,responseEntity.getBody());
    }

    @Test
    @DisplayName("전체 카테고리 보기")
    void getCategoryList() {
        //given

        //when
        ResponseEntity<?> responseEntity = categoryService.getCategoryList();

        //then
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}