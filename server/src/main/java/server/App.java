package server;

import common.utility.FlatReader;
import common.utility.UserConsole;
import server.commands.CommandManager;
import server.utility.CollectionManager;
import server.utility.FileManager;
import server.utility.RequestHandler;
import server.utility.ServerConsole;

import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    private static Scanner scanner;
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final String ENV_VARIABLE = "LABA";
    public static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        FlatReader flatReader = new FlatReader(scanner); //зачем

        FileManager fileManager = new FileManager(ENV_VARIABLE); //а он вообще нужен на сервере?
        CollectionManager collectionManager = new CollectionManager(fileManager);

        ServerConsole serverConsole = new ServerConsole();
        CommandManager commandManager = new CommandManager(serverConsole, collectionManager, flatReader, fileManager);

        RequestHandler requestHandler = new RequestHandler(commandManager, serverConsole);

        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
