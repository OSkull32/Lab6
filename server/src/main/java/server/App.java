package server;

import common.data.Flat;
import server.commands.CommandManager;
import server.utility.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.logging.Logger;

public class App {
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    public static final Path FILE_PATH = Paths.get("my_collection.json");
    public static Hashtable<Integer, Flat> hashtable;

    public static void main(String[] args) {

        try { //получение hashtable
            hashtable = JsonParser.decode(ServerFileManager.readFromFile(
                    ServerFileManager.addFile(FILE_PATH)));
        } catch (Exception e) {
            logger.severe("ошибка при чтении из файла");
            System.exit(0);
        }

        ServerConsole serverConsole = new ServerConsole();

        CollectionManager collectionManager = new CollectionManager(hashtable, serverConsole);
        CommandManager commandManager = new CommandManager(serverConsole, collectionManager);

        RequestHandler requestHandler = new RequestHandler(commandManager, serverConsole);

        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler, collectionManager);

        Thread mainThread = Thread.currentThread();
        Thread controllingServerThread = new Thread(() -> server.controlServer(mainThread));
        controllingServerThread.start();

        server.run();
    }
}
