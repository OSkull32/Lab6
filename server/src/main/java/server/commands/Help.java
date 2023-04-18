package server.commands;

import common.exceptions.WrongArgumentException;

/**
 * Класс команды "help".
 *
 * @author Kliodt Vadim
 * @version 1.1
 */
public class Help implements Command {
    private final CommandManager COMMAND_MANAGER;

    /**
     * Конструирует объект, привязывая его к конкретному объекту {@link CommandManager}.
     *
     * @param commandManager указывает на объект {@link CommandManager}, в котором
     *                       будет вызываться соответствующий метод {@link CommandManager#getCommandsInfo()}.
     */
    public Help(CommandManager commandManager) {
        this.COMMAND_MANAGER = commandManager;
    }

    /**
     * Выполняет команду "help".
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (!args.isEmpty()) throw new WrongArgumentException();
        COMMAND_MANAGER.getCommandsInfo();
    }

    /**
     * Метод, описывающий работу команды
     *
     * @return Возвращает описание команды
     */
    @Override
    public String getDescription() {
        return "выводит справку по доступным командам";
    }
}
