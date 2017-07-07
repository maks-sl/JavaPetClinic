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
 * Сервлет изменения животного
 */
public class PetEditServlet extends HttpServlet {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pet pet = this.storages.petStorage.get(Integer.valueOf(req.getParameter("petId")));
        req.setAttribute("pet", pet);
        req.setAttribute("petTypes", this.storages.petTypeStorage.values());
        RequestDispatcher disp = req.getRequestDispatcher("/views/pet/PetEdit.jsp");
        disp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pet currentPet = this.storages.petStorage.get(Integer.valueOf(req.getParameter("petId")));
        String petName = req.getParameter("petName");
        int petTypeId = Integer.valueOf(req.getParameter("petTypeId"));
        currentPet.setName(petName);
        currentPet.setPetType(storages.petTypeStorage.get(petTypeId));
        this.storages.petStorage.edit(currentPet);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+currentPet.getOwner().getId()));
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
