package ru.lesson.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.store.Storages;

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

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

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
            if (petName.equals("")) req.setAttribute("clients", storages.clientStorage.searchByName(clientName));
            else if (clientName.equals("")) req.setAttribute("clients", storages.clientStorage.searchByPetName(petName));
            else if (isAnd == null) req.setAttribute("clients", storages.clientStorage.searchOr(clientName, petName));
            else req.setAttribute("clients", storages.clientStorage.searchAnd(clientName, petName));

            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/clinic/ClinicView.jsp");
            dispatcher.forward(req, resp);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
