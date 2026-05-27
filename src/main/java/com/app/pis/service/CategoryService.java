package com.app.pis.service;
import com.app.pis.dto.request.CategoryRequest;
import com.app.pis.dto.response.CategoryResponse;
import com.app.pis.entity.Category;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.CategoryMapper;
import com.app.pis.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategory () {
        return categoryRepository
                                .getAll()
                                .map(categoryMapper::toResponse)
                                .toList();
    }


    @Transactional
    public CategoryResponse createCategory (CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BadRequestException("Category name already exists");
        }
        Category category = categoryRepository.save(categoryMapper.toEntity(request));
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse updateCategory (Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new BadRequestException("Category not found"));
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.note() != null) {
            category.setNote(request.note());
        }
        Category categoryUpdate = categoryRepository.save(category);
        return categoryMapper.toResponse(categoryUpdate);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new BadRequestException("Category not found"));
        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new BadRequestException("Category delete unit");
        }
    }



}
