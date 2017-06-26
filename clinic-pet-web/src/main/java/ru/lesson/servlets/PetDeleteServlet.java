package ru.lesson.servlets;

import ru.lesson.lessons.Cat;
import ru.lesson.lessons.Dog;
import ru.lesson.lessons.Pet;
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
public class PetDeleteServlet extends HttpServlet {

//    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();
    private final PetCache PET_CACHE = PetCache.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pet pet = this.PET_CACHE.get(Integer.valueOf(req.getParameter("petId")));
        int clientId = pet.getClientId();
        this.PET_CACHE.delete(Integer.valueOf(req.getParameter("petId")));

        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+clientId));
    }
}
