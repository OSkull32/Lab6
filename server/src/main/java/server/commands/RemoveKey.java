package server.commands;

import common.utility.CollectionManager;
import common.exceptions.WrongArgumentException;
import common.utility.Console;

/**
 * Класс команды, которая удаляет элемент
 */
public class RemoveKey implements Command{
    private final CollectionManager collectionManager;
    private final Console CONSOLE;

    /**
     * Конструктор класса.
     *
     * @param collectionManager Хранит ссылку на объект CollectionManager.
     */
    public RemoveKey(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.CONSOLE = console;
    }

    /**
     * Метод, удаляющий элемент коллекции, по значению ключа
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (args.isEmpty()) throw new WrongArgumentException();
        try {
            if (collectionManager.containsKey(Integer.parseInt(args))) {
                collectionManager.removeKey(Integer.parseInt(args));
                CONSOLE.printCommandTextNext("Элемент коллекции был удален.");
            } else CONSOLE.printCommandTextNext("Данного элемента коллекции не существует");
        } catch (IndexOutOfBoundsException ex) {
            CONSOLE.printCommandError("Не указаны аргументы команды");
        } catch (NumberFormatException ex) {
            CONSOLE.printCommandError("Формат аргумента не соответствует целочисленному " + ex.getMessage());
        }
    }

    /**
     * @return Возвращает описание команды
     * @see Command
     */
    @Override
    public String getDescription() {
        return "удаляет элемент с указанным ключом";
    }
}
