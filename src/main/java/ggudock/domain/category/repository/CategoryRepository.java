package ggudock.domain.category.repository;

import ggudock.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryById(Long categoryId);

}
