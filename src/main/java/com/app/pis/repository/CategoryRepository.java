package com.app.pis.repository;

import com.app.pis.entity.Category;
import com.app.pis.entity.ImportReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

    @Query("select category from Category category")
    Stream<Category> getAll();
}
