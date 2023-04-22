package server.utility;

import common.exceptions.CommandUsageException;
import common.interaction.requests.Request;
import common.interaction.responses.Response;
import common.interaction.responses.ResponseCode;
import common.utility.UserConsole;
import server.commands.CommandManager;

/**
 * Класс, который обрабатывает запросы
 */
public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    /**
     * Выполняет команду из запроса
     *
     * @param command имя команды
     * @param commandStringArgument String аргумент команды
     * @param commandObjectArgument Object аргумента команды
     * @return статус исполнения
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        //TODO Нужно сделать что-то на подобии метода processCommand в UserHandler
        return ResponseCode.OK;
    }
}
