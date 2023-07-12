package ru.manturov.api.converter;

import org.springframework.stereotype.Component;
import ru.manturov.api.json.CategoryResponse;
import ru.manturov.entity.Category;

@Component
public class CategoryToResponseConverter implements Converter<Category, CategoryResponse> {

    @Override
    public CategoryResponse convert(Category category) {
        return new CategoryResponse(category.getId(), category.getCategory());
    }
}
