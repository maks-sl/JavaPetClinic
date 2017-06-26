package ru.lesson.servlets;

import ru.lesson.lessons.Cat;
import ru.lesson.lessons.Dog;
import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetType;
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
public class PetCreateServlet extends HttpServlet {

    private final PetCache PET_CACHE = PetCache.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int clientId = Integer.valueOf(req.getParameter("clientId"));
        String petTypeName = req.getParameter("petTypeName");
        String petName = req.getParameter("petName");
        PET_CACHE.add(clientId,petName, PetType.getTypeByName(petTypeName));
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+clientId));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientId", Integer.valueOf(req.getParameter("clientId")));
        req.setAttribute("petTypes", PetType.values());
        RequestDispatcher disp = req.getRequestDispatcher("/views/pet/PetCreate.jsp");
        disp.forward(req, resp);
    }
}
