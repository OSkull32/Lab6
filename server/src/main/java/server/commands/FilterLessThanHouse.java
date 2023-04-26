package server.commands;

import common.data.Flat;
import common.data.House;
import common.exceptions.WrongArgumentException;
import common.utility.Console;
import server.utility.CollectionManager;

import java.util.Collection;

/**
 * Класс команды "filter_less_than_house".
 *
 * @author Kliodt Vadim
 * @version 1.0
 */

public class FilterLessThanHouse implements Command {

    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    /**
     * Конструирует объект, привязывая его к конкретному объекту {@link CollectionManager}.
     *
     * @param collectionManager указывает на объект {@link CollectionManager}.
     */
    public FilterLessThanHouse(CollectionManager collectionManager, Console console, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Метод запускает исполнение команды "filter_less_then_house".
     *
     * @param args Строка, содержащая переданные команде аргументы.
     * @throws WrongArgumentException при неправильном аргументе.
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (!args.isEmpty()) throw new WrongArgumentException();
        try {
            int year;
            Long numberOfFloors;
            long numberOfFlatsOnFloor;
            Long numberOfLifts;

            Object obj = commandManager.getCommandObjectArgument();
            if (obj instanceof House house) { //pattern variable
                year = house.getYear();
                numberOfFloors = house.getNumberOfFloors();
                numberOfFlatsOnFloor = house.getNumberOfFloors();
                numberOfLifts = house.getNumberOfLifts();
            } else {
                throw new WrongArgumentException("Объект аргумента не соответствует типу House");
            }

            Collection<Flat> flatCollection = collectionManager.getCollection().values();
            int countHouse = 0;
            for (Flat flat : flatCollection) {
                if (flat.getHouse() == null) continue;
                if (flat.getHouse().getYear() < year
                        && flat.getHouse().getNumberOfFloors() < numberOfFloors
                        && flat.getHouse().getNumberOfFlatsOnFloor() < numberOfFlatsOnFloor
                        && flat.getHouse().getNumberOfLifts() < numberOfLifts) {
                    countHouse++;
                    console.printCommandTextNext(flat.getName() + "; ");
                }
            }
            if (countHouse == 0) console.printCommandTextNext("Таких домов нет.");

        } catch (NumberFormatException e) {
            throw new WrongArgumentException("Аргумент должен быть числом.");
        } catch (ArrayIndexOutOfBoundsException ex) {
            console.printCommandError("Не указаны аргументы команды, необходимо ввести 4 аргумента через пробел");
        }
    }

    /**
     * Получить описание команды.
     *
     * @return описание команды.
     */
    @Override
    public String getDescription() {
        return "Вывести элементы, значение поля house которых меньше заданного";
    }
}
