package server;

import common.data.Flat;
import common.utility.FlatReader;
import server.commands.CommandManager;
import server.utility.*;
import common.utility.FileManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    //private static final Scanner scanner = new Scanner(System.in);
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    public static final String FILE_PATH = "my_resources\\my_collection.json";
    public static Hashtable<Integer, Flat> hashtable;

    public static void main(String[] args) {

        try { //получение hashtable
            hashtable = JsonParser.decode(ServerFileManager.readFromFile(
                    ServerFileManager.addFile(Paths.get(FILE_PATH))));
        } catch (Exception e) {
            logger.severe("ошибка при чтении из файла");
            System.exit(0);
        }

        CollectionManager collectionManager = new CollectionManager(hashtable);

        ServerConsole serverConsole = new ServerConsole();
        CommandManager commandManager = new CommandManager(serverConsole, collectionManager);

        RequestHandler requestHandler = new RequestHandler(commandManager, serverConsole);

        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
