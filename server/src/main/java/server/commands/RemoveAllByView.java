package server.commands;

import server.utility.CollectionManager;
import common.data.View;
import common.exceptions.WrongArgumentException;
import common.utility.Console;

/**
 * Класс команды, удаляющая элементы вид которых, соответствует заданному
 */
public class RemoveAllByView implements Command{
    private final CollectionManager collectionManager;
    private final Console CONSOLE;

    /**
     * Конструктор класса.
     *
     * @param collectionManager Хранит ссылку на объект CollectionManager.
     */
    public RemoveAllByView(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.CONSOLE = console;
    }

    /**
     * Метод, удаляющий элементы коллекции с соответствующим полем view
     *
     * @param args Строка, содержащая переданные команде аргументы.
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (args.isEmpty()) throw new WrongArgumentException();
        try {
            if (args.equals("null")) {
                collectionManager.removeAllByView(null);
            }
            else collectionManager.removeAllByView(View.valueOf(args));
        } catch (IllegalArgumentException ex) {
            CONSOLE.printCommandError("Выбранной константы нет в перечислении.");
            CONSOLE.printCommandTextNext("Список всех констант:");
            for (View view : View.values()) {
                CONSOLE.printCommandTextNext(view.toString());
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            CONSOLE.printCommandError("Не указаны аргументы команды.");
        }
    }

    /**
     * @return описание команды.
     *
     * @see Command
     */
    @Override
    public String getDescription() {
        return "удаляет элементы коллекции, поля View которого соответствуют введенному";
    }
}
