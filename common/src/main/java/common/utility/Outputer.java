package common.utility;

/**
 * Класс для вывода
 */
public class Outputer {
    /**
     * Выводит объект
     *
     * @param toOut объект, который будет выводиться
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Переход на следующую строку
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Выводиться объект и осуществляется переход на следующую строку
     *
     * @param toOut объект, который будет выводиться
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Выводит ошибку
     *
     * @param toOut ошибка
     */
    public static void printError(Object toOut) {
        System.out.println("error: " + toOut);
    }

    /**
     * Выводит отформатированную 2-элементную таблицу в консоль
     *
     * @param element1 Левый элемент в строке
     * @param element2 Правый элемент в строке
     */
    public static void printTable(Object element1, Object element2) {
        System.out.printf("%-37s%-1s%n", element1, element2);
    }
}