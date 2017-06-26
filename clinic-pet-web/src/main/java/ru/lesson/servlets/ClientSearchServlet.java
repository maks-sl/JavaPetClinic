package ru.lesson.servlets;

import ru.lesson.store.ClientCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет поиска клиентов
 */
public class ClientSearchServlet extends HttpServlet{

    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientName = req.getParameter("findClientName");
        String petName = req.getParameter("findPetName");
        String isAnd = req.getParameter("isAnd");
        req.setAttribute("findClientName", clientName);
        req.setAttribute("findPetName", petName);
        req.setAttribute("andChecked", (isAnd!=null)?"checked":"");
        if(clientName.equals("") && petName.equals("")){
            resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/view"));
        }
        else {
            if (petName.equals("")) req.setAttribute("clients", CLIENT_CACHE.searchByName(clientName));
            else if (clientName.equals("")) req.setAttribute("clients", CLIENT_CACHE.searchByPetName(petName));
            else if (isAnd == null) req.setAttribute("clients", CLIENT_CACHE.searchOr(clientName, petName));
            else req.setAttribute("clients", CLIENT_CACHE.searchAnd(clientName, petName));

            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/clinic/ClinicView.jsp");
            dispatcher.forward(req, resp);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        CLIENT_CACHE.close();
    }


}
