package ggudock.domain.category.api;

import ggudock.domain.category.api.request.CategoryRequest;
import ggudock.domain.category.application.CategoryService;
import ggudock.domain.category.application.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryRequest), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(null,HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<CategoryResponse> getDetail(@RequestParam("categoryId") Long categoryId) {
        return new ResponseEntity<>(categoryService.getDetail(categoryId),HttpStatusCode.valueOf(200));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategoryList() {
        return new ResponseEntity<>(categoryService.getCategoryList(),HttpStatusCode.valueOf(200));
    }
}
