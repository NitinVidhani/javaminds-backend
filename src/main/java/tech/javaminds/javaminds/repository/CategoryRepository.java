package tech.javaminds.javaminds.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.javaminds.javaminds.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findCategoryByName(String name);

    Category findTopByOrderByIdAsc();

}
