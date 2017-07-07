package ru.lesson.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.models.Pet;
import ru.lesson.store.Storages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет создания животного
 */
public class PetCreateServlet extends HttpServlet {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientId = Integer.valueOf(req.getParameter("clientId"));
        int petTypeId = Integer.valueOf(req.getParameter("petTypeId"));
        String petName = req.getParameter("petName");
        Pet newPet = new Pet(
                0,
                petName,
                storages.petTypeStorage.get(petTypeId),
                storages.clientStorage.get(clientId)
        );
        storages.petStorage.add(newPet);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+clientId));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientId", Integer.valueOf(req.getParameter("clientId")));
        req.setAttribute("petTypes", this.storages.petTypeStorage.values());
        RequestDispatcher disp = req.getRequestDispatcher("/views/pet/PetCreate.jsp");
        disp.forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
