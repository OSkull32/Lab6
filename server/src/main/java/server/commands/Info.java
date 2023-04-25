package server.commands;

import server.utility.CollectionManager;
import common.exceptions.WrongArgumentException;

/**
 * Класс команды, которая выводит информацию о коллекции
 */
public class Info implements Command{
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     *
     * @param collectionManager хранит ссылку на объект CollectionManager
     */
    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, исполняющий команду. Выводит описание коллекции HashTable
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (!args.isEmpty()) throw new WrongArgumentException();
        collectionManager.info();
    }

    /**
     * @see Command
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Команда выводит информацию о коллекции";
    }
}
