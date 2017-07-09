package ru.lesson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.models.Client;
import ru.lesson.models.SearchForm;
import ru.lesson.store.Storages;

/**
 * Контроллер клиента клиники
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private Storages storages;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute Client client) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        //current user
//        String login = auth.getName();
        this.storages.clientStorage.add(client);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editGet(@RequestParam("id") String id, ModelMap model) {
        model.addAttribute("client", this.storages.clientStorage.get(Integer.valueOf(id)));
        model.addAttribute("clientPets", this.storages.petStorage.getByClientId(Integer.valueOf(id)));
        return "client/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@ModelAttribute Client client) {
        Client oldClient = this.storages.clientStorage.get(client.getId());
        client.setPets(oldClient.getPets());
        this.storages.clientStorage.edit(client);

        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") String id) {
        this.storages.clientStorage.delete(Integer.valueOf(id));
        return "redirect:/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@ModelAttribute SearchForm sf, ModelMap model) {

        String toReturn = "redirect:/";
        String clientName = sf.getFindClientName();
        String petName = sf.getFindPetName();
        String isAnd = sf.getIsAnd();
        if(!clientName.equals("") || !petName.equals("")){

            model.addAttribute("findClientName", sf.getFindClientName());
            model.addAttribute("findPetName", sf.getFindPetName());
            model.addAttribute("andChecked", (sf.getIsAnd()!=null)?"checked":"");

            if (petName.equals("")) model.addAttribute("clients", storages.clientStorage.searchByName(clientName));
            else if (clientName.equals("")) model.addAttribute("clients", storages.clientStorage.searchByPetName(petName));
            else if (isAnd == null) model.addAttribute("clients", storages.clientStorage.searchOr(clientName, petName));
            else model.addAttribute("clients", storages.clientStorage.searchAnd(clientName, petName));
            toReturn = "clinic/view";
        }
        return toReturn;
    }
}