package server.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.servlets.auth.AuthenticationFilter;


/**
 * Created by Ольга on 17.10.2016.
 */
public class ApiServlet {
    public static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS); //что тут делаем? Создаём обработчик всех запросов? зачем в параметрах SESSIONS
        context.setContextPath("/"); //указываем адрес, по которому будем обрабатывать запросы?
        Server jettyServer = new Server(8080); //создаём сервер на порту 8080
        jettyServer.setHandler(context); //устанавливаем обработчик запросов на сервер?
//непонятная часть кода
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*"); //ServletConteiner is httpServlet
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(   //что за параметры мы устанавливаем?
                "jersey.config.server.provider.packages",
                "server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthenticationFilter.class.getCanonicalName()
        );
//непонятная часть кода

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private ApiServlet() { //зачем нам тут приватный конструктор?
    }
}
