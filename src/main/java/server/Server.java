package server;

import org.jetbrains.annotations.NotNull;
import server.api.ApiServlet;

/**
 * Created by Ольга on 17.10.2016.
 */
public class Server {

        public static void main(@NotNull String[] args) throws Exception {
            ApiServlet.start();
        }
}
