package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {

    private int userId = SecurityUtil.authUserId();
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {this.service = service; }

    public List<Meal> get() {
        return service.getAll(userId);
    }

    public Meal get(int id) {
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        return service.create(meal, userId);
    }

    public void update(Meal meal) {
        service.update(meal, userId);
    }
}