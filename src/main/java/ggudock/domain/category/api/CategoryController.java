package ggudock.domain.category.api;

import ggudock.domain.category.api.dto.SaveForm;
import ggudock.domain.category.application.CategoryService;
import ggudock.domain.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> saveCategory(SaveForm saveForm) {
        Category category = Category.builder()
                .name(saveForm.getName())
                .icon(saveForm.getIcon())
                .build();
        return categoryService.save(category);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> delete(@PathVariable("categoryId") Long categoryId) {
        return categoryService.delete(categoryId);
    }

    @GetMapping()
    public ResponseEntity<?> getCategory(@RequestParam("category_id") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategoryList() {
        return categoryService.getCategoryList();
    }
}
