package server.commands;

import common.exceptions.WrongArgumentException;
import common.utility.Console;

/**
 * Класс команды, которая завершает работу программы
 */
public class Exit implements Command {
    private final Console CONSOLE;

    /**
     * Конструктор класса
     *
     * @param console консоль
     */
    public Exit(Console console) {
        this.CONSOLE = console;
    }

    /**
     * Метод, исполняющий команду. Выводит сообщение о завершении работы программы
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (!args.isEmpty()) throw new WrongArgumentException();
        CONSOLE.printCommandTextNext("Завершение работы программы");
        System.exit(0);
    }

    /**
     * @return описание команды
     * @see Command
     */
    @Override
    public String getDescription() {
        return "Команда завершает программу";
    }
}
