package ru.lesson.servlets;

import ru.lesson.store.ClientCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет главной страницы клиники
 */
public class ClinicServlet extends HttpServlet{

    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clients", this.CLIENT_CACHE.values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/clinic/ClinicView.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        CLIENT_CACHE.close();
    }
}
