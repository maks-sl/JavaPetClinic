package ru.lesson.servlets;

import ru.lesson.store.ClientCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет создания клиента
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

    @Override
    public void destroy() {
        super.destroy();
        CLIENT_CACHE.close();
    }
}
