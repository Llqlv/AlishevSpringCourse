package com.llqlv.springcourse.controllers;

import com.llqlv.springcourse.entity.User;
import com.llqlv.springcourse.services.ItemService;
import com.llqlv.springcourse.services.UserService;
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

    private  final UserService userService;
    private final UserValidator userValidator;
    private final ItemService itemService;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator, ItemService itemService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.itemService = itemService;
    }


    @GetMapping()
    public String index(Model model) {
        //Получаем всех людей из DAO и отдаем на отображение
        model.addAttribute("users", userService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        //Показываем одного юзера
        model.addAttribute("user", userService.findOne(id));
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

        userService.save(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userService.update(id, user);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/people";
    }
}
