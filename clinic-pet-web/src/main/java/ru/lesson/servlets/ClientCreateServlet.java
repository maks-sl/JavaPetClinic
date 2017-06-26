package ru.lesson.servlets;

import ru.lesson.models.Client;
import ru.lesson.store.ClientCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 18.06.2017.
 */
public class ClientCreateServlet extends HttpServlet {

    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name =  req.getParameter("name");
        String surname =  req.getParameter("surname");
        String email =  req.getParameter("email");
        int gender = Integer.valueOf(req.getParameter("gender"));

        this.CLIENT_CACHE.add(name, surname, email, gender);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/clinic/ClinicView"));
    }
}
