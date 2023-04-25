package server;

import common.data.Flat;
import common.utility.FlatReader;
import server.commands.CommandManager;
import server.utility.CollectionManager;
import common.utility.FileManager;
import server.utility.RequestHandler;
import server.utility.ServerConsole;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final String ENV_VARIABLE = "LABA";
    private static Hashtable<Integer, Flat> hashtable;
    public static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        FlatReader flatReader = new FlatReader(scanner); //зачем

        FileManager fileManager = new FileManager(ENV_VARIABLE); //а он вообще нужен на сервере?
        CollectionManager collectionManager = new CollectionManager(fileManager, hashtable);

        ServerConsole serverConsole = new ServerConsole();
        CommandManager commandManager = new CommandManager(serverConsole, collectionManager, flatReader, fileManager);

        RequestHandler requestHandler = new RequestHandler(commandManager, serverConsole);

        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
