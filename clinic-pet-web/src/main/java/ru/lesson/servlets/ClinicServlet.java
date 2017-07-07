//package ru.lesson.servlets;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
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
// * Сервлет главной страницы клиники
// */
//public class ClinicServlet extends HttpServlet{
//
//    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
//    Storages storages = context.getBean(Storages.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("clients", this.storages.clientStorage.values());
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/clinic/view.jsp");
//        dispatcher.forward(req, resp);
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//}
