package server.commands;

import server.utility.CollectionManager;
import common.data.Flat;
import common.exceptions.WrongArgumentException;
import common.utility.Console;
import server.utility.SortByHouse;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс команды "print_field_ascending_house".
 *
 * @author Kliodt Vadim
 * @version 1.0
 */
public class PrintFieldAscendingHouse implements Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструирует объект, привязывая его к конкретному объекту {@link CollectionManager}.
     *
     * @param collectionManager указывает на объект {@link CollectionManager}
     */
    public PrintFieldAscendingHouse(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Метод запускает исполнение команды "print_field_ascending_house".
     *
     * @param args Строка, содержащая переданные команде аргументы.
     * @throws WrongArgumentException если команде был передан аргумент.
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (!args.isEmpty()) throw new WrongArgumentException();
        Collection<Flat> flatCollection = collectionManager.getCollection().values();
        ArrayList<Flat> flatList = new ArrayList<>(flatCollection);
        flatList.sort(new SortByHouse());
        for (Flat flat : flatList) {
            console.printCommandTextNext("Квартира: " + flat.getName() + " в доме " + flat.getHouse());
        }
    }

    /**
     * Получить описание команды.
     *
     * @return описание команды.
     */
    @Override
    public String getDescription() {
        return "выводит значения поля house всех элементов в порядке возрастания";
    }
}
