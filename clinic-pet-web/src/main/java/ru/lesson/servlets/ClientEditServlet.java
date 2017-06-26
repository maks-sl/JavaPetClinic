package ru.lesson.servlets;

import ru.lesson.models.Client;
import ru.lesson.store.ClientCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет изменения клиента
 */
public class ClientEditServlet extends HttpServlet {

    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("client", this.CLIENT_CACHE.get(Integer.valueOf(req.getParameter("id"))));
        RequestDispatcher disp = req.getRequestDispatcher("/views/client/ClientEdit.jsp");
        disp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.valueOf(req.getParameter("clientId"));
        String name =  req.getParameter("name");
        String surname =  req.getParameter("surname");
        String email =  req.getParameter("email");
        int gender = Integer.valueOf(req.getParameter("gender"));

       this.CLIENT_CACHE.edit(new Client(id, name, surname, email, gender));
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/ClinicView"));
    }

    @Override
    public void destroy() {
        super.destroy();
        CLIENT_CACHE.close();
    }
}
