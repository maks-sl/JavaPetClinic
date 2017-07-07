package ru.lesson.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.models.Pet;
import ru.lesson.store.Storages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет удаление животного
 */
public class PetDeleteServlet extends HttpServlet {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pet pet = this.storages.petStorage.get(Integer.valueOf(req.getParameter("petId")));
        int clientId = pet.getOwner().getId();
        this.storages.petStorage.delete(Integer.valueOf(req.getParameter("petId")));

        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/client/edit?id="+clientId));
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
