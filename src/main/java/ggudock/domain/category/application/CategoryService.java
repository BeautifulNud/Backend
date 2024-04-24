package ggudock.domain.category.application;

import ggudock.domain.category.api.request.CategoryRequest;
import ggudock.domain.category.application.response.CategoryResponse;
import ggudock.domain.category.entity.Category;
import ggudock.domain.category.repository.CategoryRepository;
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

    public CategoryResponse saveCategory(CategoryRequest categoryRequest){
        Category category = createCategory(categoryRequest);
        categoryRepository.save(category);
        return getDetail(category.getId());
    }

    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
        new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }


    @Transactional(readOnly = true)
    public Category getCategory(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 카테고리를 찾을수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .toList();
    }

    public CategoryResponse getDetail(Long categoryId){
        return createResponse(categoryId);
    }

    private CategoryResponse createResponse(Long categoryId) {
        Category category = getCategory(categoryId);
        return CategoryResponse.builder()
                .name(category.getName())
                .icon(category.getIcon())
                .build();
    }

    private static Category createCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .icon(categoryRequest.getIcon())
                .build();
    }
}