package ru.manturov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manturov.dao.CategoryDao;
import ru.manturov.dao.ReportModel;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;
import ru.manturov.exception.NoEntityException;
import ru.manturov.repository.CategoryRepository;
import ru.manturov.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final UserRepository userRepository;
    private final CategoryDao categoryDao;

    public Category insert(String name, long userId) {
        Category category = null;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            category = new Category();
            category.setCategory(name);
            category.setUser(user);
            repository.save(category);
        }
        return category;
    }

    public Category update(long id, String name, long userId) {
        Category category = null;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            category = repository.findById(id).orElseThrow(() -> new NoEntityException(id));
            if (category != null) {
                category.setCategory(name);
                repository.save(category);
            }
        }
        return category;
    }

    public boolean delete(long id, long userId) {
        Category category;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            category = repository.findById(id).orElseThrow(() -> new NoEntityException(id));
            if (category != null) {
                repository.delete(category);
                return true;
            }
        }
        return false;
    }

    public List<ReportModel> getIncomeByPeriod(long userId, Date beginDate, Date endDate) {
        return categoryDao.getIncomeByPeriod(userId, beginDate, endDate);
    }

    public List<ReportModel> getExpenseByPeriod(long userId, Date beginDate, Date endDate) {
        return categoryDao.getExpenseByPeriod(userId, beginDate, endDate);
    }

    public List<Category> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
