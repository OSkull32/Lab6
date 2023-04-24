package server.commands;

import common.utility.CollectionManager;
import common.exceptions.WrongArgumentException;
import common.utility.Console;

/**
 * Класс команды, удаляющей элементы, ключ которых больше заданного.
 */
public class RemoveGreaterKey implements Command {
    private final CollectionManager collectionManager;
    private final Console CONSOLE;

    /**
     * Конструктор класса.
     *
     * @param collectionManager Хранит ссылку на объект CollectionManager.
     */
    public RemoveGreaterKey(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.CONSOLE = console;
    }

    /**
     * Метод, удаляющий все элементы коллекции, значения id которых больше заданного ключа
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (args.isEmpty()) throw new WrongArgumentException();
        try {
            collectionManager.removeGreaterKey(Integer.parseInt(args));
            CONSOLE.printCommandTextNext("Элементы коллекции были удалены.");
        } catch (IndexOutOfBoundsException ex) {
            CONSOLE.printCommandError("Не указан аргумент команды");
        } catch (NumberFormatException ex) {
            CONSOLE.printCommandError("Формат аргумента не соответствует целочисленному " + ex.getMessage());
        }
    }

    /**
     * @return описание команды.
     * @see Command
     */
    @Override
    public String getDescription() {
        return "удаляет все элементы коллекции, значение id которых больше указанного ключа";
    }
}
