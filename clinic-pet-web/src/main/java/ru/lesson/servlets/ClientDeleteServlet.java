package ru.lesson.servlets;

import ru.lesson.lessons.Cat;
import ru.lesson.lessons.Dog;
import ru.lesson.lessons.Pet;
import ru.lesson.models.Client;
import ru.lesson.store.ClientCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 18.06.2017.
 */
public class ClientDeleteServlet extends HttpServlet {

    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.CLIENT_CACHE.delete(Integer.valueOf(req.getParameter("id")));
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/view"));
    }

}
