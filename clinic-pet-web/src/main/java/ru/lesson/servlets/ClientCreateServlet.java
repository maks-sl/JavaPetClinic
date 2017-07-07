package ru.lesson.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.store.Storages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет создания клиента
 */
public class ClientCreateServlet extends HttpServlet {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name =  req.getParameter("name");
        String surname =  req.getParameter("surname");
        String email =  req.getParameter("email");
        int gender = Integer.valueOf(req.getParameter("gender"));

        this.storages.clientStorage.add(name, surname, email, gender);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/clinic/ClinicView"));
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
