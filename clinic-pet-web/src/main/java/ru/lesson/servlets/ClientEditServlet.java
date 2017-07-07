//package ru.lesson.servlets;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import ru.lesson.models.Client;
//import ru.lesson.store.Storages;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Сервлет изменения клиента
// */
//public class ClientEditServlet extends HttpServlet {
//
//    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
//    Storages storages = context.getBean(Storages.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("client", this.storages.clientStorage.get(Integer.valueOf(req.getParameter("id"))));
//        req.setAttribute("clientPets", this.storages.petStorage.getByClientId(Integer.valueOf(req.getParameter("id"))));
//        RequestDispatcher disp = req.getRequestDispatcher("/views/client/edit.jsp");
//        disp.forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        int id = Integer.valueOf(req.getParameter("id"));
//        String name =  req.getParameter("name");
//        String surname =  req.getParameter("surname");
//        String email =  req.getParameter("email");
//        int gender = Integer.valueOf(req.getParameter("gender"));
//
//        Client newClient = this.storages.clientStorage.get(id);
//        newClient.setName(name);
//        newClient.setSurname(surname);
//        newClient.setEmail(email);
//        newClient.setGender(gender);
//
//       this.storages.clientStorage.edit(newClient);
//        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/"));
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//}
