package ru.lesson.servlets;

import ru.lesson.lessons.*;
import ru.lesson.models.Client;
import ru.lesson.store.ClientCache;
import ru.lesson.store.PetCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 18.06.2017.
 */
public class PetEditServlet extends HttpServlet {

    private final PetCache PET_CACHE = PetCache.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pet pet = this.PET_CACHE.get(Integer.valueOf(req.getParameter("petId")));
        req.setAttribute("pet", pet);
        req.setAttribute("petTypes", PetType.values());
        RequestDispatcher disp = req.getRequestDispatcher("/views/pet/PetEdit.jsp");
        disp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pet currentPet = this.PET_CACHE.get(Integer.valueOf(req.getParameter("petId")));
        String petTypeName = req.getParameter("petTypeName");
        String petName = req.getParameter("petName");
        Pet newPet = PetGenerator.createPet( currentPet.getId(), currentPet.getClientId(), petName, PetType.getTypeByName(petTypeName) );
        this.PET_CACHE.edit(newPet);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+newPet.getClientId()));

    }
}
