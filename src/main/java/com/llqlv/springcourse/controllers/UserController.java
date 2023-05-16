package com.llqlv.springcourse.controllers;

import com.llqlv.springcourse.dao.UserDao;
import com.llqlv.springcourse.entity.User;
import com.llqlv.springcourse.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class UserController {

    private final UserDao userDao;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model) {
        //Получаем всех людей из DAO и отдаем на отображение
        model.addAttribute("users", userDao.getAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        //Показываем одного юзера
        model.addAttribute("user", userDao.getPersonById(id).get());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }

        userDao.save(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("user", userDao.getPersonById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userDao.update(id, user);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/people";
    }
}
