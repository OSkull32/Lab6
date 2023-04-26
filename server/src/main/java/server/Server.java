package server;

import common.exceptions.ClosingSocketException;
import common.exceptions.ConnectionErrorException;
import common.exceptions.OpeningServerSocketException;
import common.interaction.requests.Request;
import common.interaction.responses.Response;
import common.interaction.responses.ResponseCode;
import common.utility.UserConsole;
import server.utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Клас, который запускает сервер
 */
public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

    public void run() {
        try {
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException ex) {
                    break;
                } catch (IOException ex) {
                    UserConsole.printCommandError("Ошибка при попытке завершить соединение с клиентом");
                    App.logger.severe("Ошибка при попытке завершить соединение с клиентом");
                }
            }
            stop();
        } catch (OpeningServerSocketException ex) {
            UserConsole.printCommandError("Сервер не может быть запущен");
            App.logger.severe("Сервер не может быть запущен");
        }
    }

    /**
     * Завершает работу сервера
     */
    private void stop() {
        try {
            App.logger.info("Завершение работы сервера...");
            if (serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            UserConsole.printCommandTextNext("Работа сервера успешно завершена");
            App.logger.info("Работа сервера успешно завершена");
        } catch (ClosingSocketException ex) {
            UserConsole.printCommandError("Невозможно завершить работу еще не запущенного сервера");
            App.logger.severe("Невозможно завершить работу еще не запущенного сервера");
        } catch (IOException ex) {
            UserConsole.printCommandError("Произошла ошибка при завершении работы сервера");
            App.logger.severe("Произошла ошибка при завершении работы сервера");
        }
    }

    /**
     * Открытие сокета сервера
     *
     * @throws OpeningServerSocketException сокет сервера не может быть открыт
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            App.logger.info("Запуск сервера");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            App.logger.info("Сервер успешно запущен");
        } catch (IllegalArgumentException ex) {
            UserConsole.printCommandError("Порт '" + port + "' не валидное значение порта");
            App.logger.severe("Порт '" + port + "' не валидное значение порта");
            throw new OpeningServerSocketException();
        } catch (IOException ex) {
            UserConsole.printCommandError("При попытке использовать порт возникла ошибка " + port);
            App.logger.severe("При попытке использовать порт возникла ошибка " + port);
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Подключение к клиенту
     *
     * @return clientSocket
     * @throws ConnectionErrorException Ошибка при подключении
     * @throws SocketTimeoutException   Превышено время ожидания подключения
     */
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            UserConsole.printCommandTextNext("Попытка соединения с портом '" + port + "'...");
            App.logger.info("Попытка соединения с портом '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            UserConsole.printCommandTextNext("Соединение с клиентом успешно установлено");
            App.logger.info("Соединение с клиентом успешно установлено");
            return clientSocket;
        } catch (SocketTimeoutException ex) {
            UserConsole.printCommandError("Превышено время ожидания подключения");
            App.logger.warning("Превышено время ожидания подключения");
            throw new SocketTimeoutException();
        } catch (IOException ex) {
            UserConsole.printCommandError("Произошла ошибка при соединении с клиентом!");
            App.logger.severe("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Процесс получения запроса от клиента
     *
     * @param clientSocket сокет клиента
     * @return получили ли запрос
     */
    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                App.logger.info("Запрос '" + userRequest.getCommandName() + "' успешно обработан");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException ex) {
            UserConsole.printCommandError("Ошибка при чтении полученных данных");
            App.logger.severe("Ошибка при чтении полученных данных");
        } catch (InvalidClassException | NotSerializableException ex) {
            UserConsole.printCommandError("Ошибка при отправке данных на клиент");
            App.logger.severe("Ошибка при отправке данных на клиент");
        } catch (IOException ex) {
            if (userRequest == null) {
                UserConsole.printCommandError("Разрыв соединения с клиентом");
                App.logger.warning("Разрыв соединения с клиентом");
            } else {
                UserConsole.printCommandText("Клиент успешно отключен от сервера");
                App.logger.info("Клиент успешно отключен от сервера");
            }
        }
        return true;
    }
}
