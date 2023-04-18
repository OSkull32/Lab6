package server.commands;

import server.utility.CollectionManager;
import common.data.Flat;
import common.exceptions.WrongArgumentException;
import common.utility.Console;
import common.utility.FlatReader;

/**
 * Класс команды, которая добавляет элемент в коллекцию с заданным ключом
 */
public class Insert implements Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final FlatReader flatReader;

    /**
     * Конструктор класса.
     *
     * @param collectionManager  Хранит ссылку на объект CollectionManager.
     * @param console            Хранит ссылку на объект класса Console.
     * @param flatReader Хранит ссылку на объект, осуществляющий чтение полей из указанного в console потока ввода.
     */
    public Insert(CollectionManager collectionManager, Console console, FlatReader flatReader) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.flatReader = flatReader;
    }

    /**
     * Метод, исполняющий команду. При запуске команды запрашивает ввод указанных полей.
     * При успешном выполнении команды в потоке вывода высветится уведомление о добавлении элемента в коллекцию.
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (args.isEmpty()) throw new WrongArgumentException();
        try {
            if (!collectionManager.containsKey(Integer.parseInt(args))) {
                console.printCommandTextNext("Введите значения полей для элемента коллекции");
                Flat flat = flatReader.read();
                collectionManager.insert(Integer.parseInt(args), flat);
                console.printCommandTextNext("Элемент добавлен в коллекцию");
            } else console.printCommandError("Элемент с данным ключом уже существует в коллекции");
        } catch (IndexOutOfBoundsException ex) {
            console.printCommandError("Не указаны аргументы команды.");
        } catch (NumberFormatException ex) {
            console.printCommandError("Формат аргумента не соответствует целочисленному " + ex.getMessage());
        }
    }

    /**
     * @return Возвращает описание данной команды.
     * @see Command
     */
    @Override
    public String getDescription() {
        return "добавляет элемент с указанным ключом в качестве атрибута";
    }
}
