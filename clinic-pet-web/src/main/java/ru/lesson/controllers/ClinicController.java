package ru.lesson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.lesson.store.Storages;

/**
 * Контроллер главной страницы клиники
 */
@Controller
@RequestMapping("")
public class ClinicController {

    @Autowired
    private Storages storages;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showClients(ModelMap model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        //current user
//        String login = auth.getName();

        model.addAttribute("clients", this.storages.clientStorage.values());
        return "clinic/view";
    }
}