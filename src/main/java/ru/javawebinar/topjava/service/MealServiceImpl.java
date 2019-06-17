package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (repository.get(id) == null) {
            throw new NotFoundException("This meal doesn't exist");
        }
        if (!repository.get(id).getUserId().equals(userId)) {
            throw new NotFoundException("This meal doesn't belong to you");
        }
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        if (repository.get(id) == null) {
            throw new NotFoundException("This meal doesn't exist");
        }
        if (!repository.get(id).getUserId().equals(userId)) {
            throw new NotFoundException("This meal doesn't belong to you");
        }
        return repository.get(id);
    }

    @Override
    public void update(Meal meal, int userId) {
        if (meal.getUserId().equals(userId)) {
            checkNotFoundWithId(repository.save(meal), meal.getId());
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getByUserId(userId));
    }
}