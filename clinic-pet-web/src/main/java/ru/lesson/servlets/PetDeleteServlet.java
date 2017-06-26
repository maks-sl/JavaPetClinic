package ru.lesson.servlets;

import ru.lesson.lessons.Pet;
import ru.lesson.store.PetCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет удаление животного
 */
public class PetDeleteServlet extends HttpServlet {

    private final PetCache PET_CACHE = PetCache.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pet pet = this.PET_CACHE.get(Integer.valueOf(req.getParameter("petId")));
        int clientId = pet.getClientId();
        this.PET_CACHE.delete(Integer.valueOf(req.getParameter("petId")));

        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+clientId));
    }

    @Override
    public void destroy() {
        super.destroy();
        PET_CACHE.close();
    }
}
