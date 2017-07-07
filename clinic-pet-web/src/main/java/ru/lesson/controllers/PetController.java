package ru.lesson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.models.Pet;
import ru.lesson.models.PetForm;
import ru.lesson.store.Storages;

/**
 * Created by User on 07.07.2017.
 */
@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private Storages storages;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createGet(@RequestParam("clientId") String clientId, ModelMap model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("petTypes", this.storages.petTypeStorage.values());
        return "pet/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(@ModelAttribute PetForm pf) {
        Pet pet = new Pet(
                0,
                pf.getPetName(),
                storages.petTypeStorage.get(Integer.valueOf(pf.getPetTypeId())),
                storages.clientStorage.get(Integer.valueOf(pf.getClientId()))
        );

        this.storages.petStorage.add(pet);
        return "redirect:/client/edit?id="+pet.getOwner().getId();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editGet(@RequestParam("petId") String petId, ModelMap model) {
        model.addAttribute("pet", this.storages.petStorage.get(Integer.valueOf(petId)));
        model.addAttribute("petTypes", this.storages.petTypeStorage.values());
        return "pet/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@ModelAttribute PetForm pf) {
        Pet pet = this.storages.petStorage.get(Integer.valueOf(pf.getPetId()));
        pet.setName(pf.getPetName());
        pet.setPetType(storages.petTypeStorage.get(Integer.valueOf(pf.getPetTypeId())));
        this.storages.petStorage.edit(pet);
        return "redirect:/client/edit?id="+pet.getOwner().getId();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("petId") String id) {
        Pet pet = this.storages.petStorage.get(Integer.valueOf(id));
        int clientId = pet.getOwner().getId();
        this.storages.petStorage.delete(Integer.valueOf(id));
        return "redirect:/client/edit?id="+clientId;
    }


}