package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(this::save);
        repository.values().forEach(meal -> meal.setUserId(1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUserId().equals(userId)) {
            log.info("save meal {} from userId: {}", meal, userId);
            if (meal.isNew()) {
                meal.setUserId(userId);
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // treat case: update, but absent in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    public Meal save(Meal meal) {

            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal from userId: {} ",userId);
        if (repository.get(id) == null || !repository.get(id).getUserId().equals(userId)) {
           return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal by id: {}, from userId: {} ",id,userId);
        if (repository.get(id) == null || !repository.get(id).getUserId().equals(userId)) {
            throw new NotFoundException("This meal doesn't exist");
        }

        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getByUserId(Integer userId) {
        log.info("get all meals by userId: {}",userId);
        return getAll().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

}

