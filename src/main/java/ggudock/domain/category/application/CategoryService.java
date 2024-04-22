package ggudock.domain.category.application;

import ggudock.domain.category.application.dto.CategoryDto;
import ggudock.domain.category.entity.Category;
import ggudock.domain.category.repository.CategoryRepository;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> save(Category category){
        categoryRepository.save(category);
        return new ResponseEntity<>(null,HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<?> delete(Long categoryId){
        if(categoryRepository.findById(categoryId).orElse(null)==null)
            return ResponseEntity.status(ErrorCode.NOT_FOUND_CATEGORY.getCode()).body(ErrorCode.NOT_FOUND_CATEGORY);
        Category category = categoryRepository.findCategoryById(categoryId);
        categoryRepository.delete(category);

        return new ResponseEntity<>(null,HttpStatusCode.valueOf(200));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategory(Long categoryId){
        if(categoryRepository.findById(categoryId).orElse(null)==null)
            return ResponseEntity.status(ErrorCode.NOT_FOUND_CATEGORY.getCode()).body(ErrorCode.NOT_FOUND_CATEGORY);
        Category category = categoryRepository.findCategoryById(categoryId);
        CategoryDto categoryDto = getCategoryDto(category);

        return new ResponseEntity<>(categoryDto,HttpStatusCode.valueOf(200));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategoryList(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(CategoryDto::new).toList();
        return new ResponseEntity<>(categoryDtoList,HttpStatusCode.valueOf(200));
    }

    private CategoryDto getCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .icon(category.getIcon())
                .build();
    }
}