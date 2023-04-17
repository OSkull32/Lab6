package client.utility;

import client.App;
import common.data.*;
import common.exceptions.ErrorInScriptException;
import common.exceptions.InvalidValueException;
import common.utility.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Класс, необходимы для чтения полей объекта Flat
 */
public class FlatReader implements FlatReaderInterface, CoordinatesReaderInterface, HouseReaderInterface{

    private boolean fileMode;

    // Поле, хранящее ссылку на объект класса типа Console
    private final UserConsole console;

    /**
     * Константа, хранящее шаблон (регулярное выражение), которому должны
     * соответствовать все поля {@link Flat} типа String
     */
    public static final String PATTERN_NAMES = "^[a-zA-Z0-9_\\-\\s\u0410-\u044f\u0451\u0401]+$";

    /**
     * Конструктор класса, который присваивает console значение, переданное в конструкторе в качестве параметра
     *
     * @param console хранит ссылку на объект типа Console
     */
    public FlatReader(UserConsole console) {
        this.console = console;
    }

    public boolean askQuestion(String question) throws ErrorInScriptException {
        String finalQuestion = question + " (yes/no):";
        String answer;

        while (true) {
            try {
                console.printCommandTextNext(finalQuestion);
                console.printCommandText(App.PS2);
                answer = console.readLine().trim();
                if (fileMode) console.printCommandTextNext(answer);
                if (!answer.equals("yes") && !answer.equals("no")) throw new InvalidValueException();
                break;
                } catch (NoSuchElementException ex) {
                console.printCommandError("Ответ не распознан");
                if (fileMode) throw new ErrorInScriptException();
            } catch (InvalidValueException ex) {
                console.printCommandError("Ответом может быть только 'yes' или 'no'");
                if (fileMode) throw new ErrorInScriptException();
            } catch (IllegalStateException ex) {
                console.printCommandError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("yes");
    }

    public void setFileMode() {
        fileMode = true;
    }

    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Метод, который читает поле name объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля name, уже проверенное на условия допустимости
     */
    @Override
    public String readName() {
        while (true) {
            console.printCommandText("name (not null): ");
            String str = console.readLine().trim();
            if (str.equals("")) console.printCommandError("Значение поля не может быть null или пустой строкой");
            else if (!str.matches(PATTERN_NAMES))
                console.printCommandError("Введенная строка содержит запрещенные символы");
            else return str;
        }
    }

    /**
     * Метод, который читает координаты x и y
     *
     * @return объект типа Coordinates
     */
    @Override
    public Coordinates readCoordinates() {
        return new Coordinates(readCoordinatesX(), readCoordinatesY());
    }

    /**
     * Метод, который читает поле x объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля x, уже проверенное на условия допустимости
     */
    @Override
    public int readCoordinatesX() {
        int x;
        while (true) {
            try {
                console.printCommandText("coordinate x(int & x <= 713): ");
                x = Integer.parseInt(console.readLine().trim());
                if (x > 713) throw new InvalidValueException();
                else return x;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Координата x должна быть не более 713");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа int");
            }
        }
    }

    /**
     * Метод, который читает поле y объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля y, уже проверенное на условия допустимости
     */
    @Override
    public Integer readCoordinatesY() {
        Integer y;
        while (true) {
            try {
                console.printCommandText("coordinate y(integer & not null & y > -397): ");
                y = Integer.parseInt(console.readLine().trim());
                if (y <= -397) throw new InvalidValueException();
                else return y;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Координата y должна быть больше -397");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа Integer и не null");
            }
        }
    }

    /**
     * Метод, который читает поле area объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля area, уже проверенное на условия допустимости
     */
    @Override
    public int readArea() {
        int area;
        while (true) {
            try {
                console.printCommandText("area(int & area > 0): ");
                area = Integer.parseInt(console.readLine().trim());
                if (area <= 0) throw new InvalidValueException();
                else return area;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение area должно быть больше 0");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа int");
            }
        }
    }

    /**
     * Метод, который читает поле numberOfRooms объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля numberOfRooms, уже проверенное на условия допустимости
     */
    @Override
    public long readNumberOfRooms() {
        long numberOfRooms;
        while (true) {
            try {
                console.printCommandText("numberOfRooms(long & 0 < numberOfRooms <=14): ");
                numberOfRooms = Long.parseLong(console.readLine().trim());
                if (numberOfRooms <= 0 || numberOfRooms > 14) throw new InvalidValueException();
                else return numberOfRooms;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение numberOfRooms должно быть больше 0 и не более 14");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа long");
            }
        }
    }

    /**
     * Метод, который читает поле numberOfBathrooms объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля numberOfBathrooms, уже проверенное на условия допустимости
     */
    @Override
    public long readNumberOfBathrooms() {
        long numberOfBathrooms;
        while (true) {
            try {
                console.printCommandText("numberOfBathrooms(long & numberOfBathrooms > 0): ");
                numberOfBathrooms = Long.parseLong(console.readLine().trim());
                if (numberOfBathrooms <= 0) throw new InvalidValueException();
                else return numberOfBathrooms;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение numberOfBathrooms должно быть больше 0");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа long");
            }
        }
    }

    /**
     * Метод, который читает поле furnish объекта Flat из потока, указанного в поле console.
     * Выводит список допустимых констант
     *
     * @return значение поля furnish, уже проверенное на условия допустимости
     */
    @Override
    public Furnish readFurnish() {
        Furnish furnish;
        while (true) {
            try {
                console.printCommandText("Допустимые значения furnish:\n");
                for (Furnish val : Furnish.values()) {
                    console.printCommandText(val.name() + "\n");
                }
                console.printCommandText("furnish: ");
                furnish = Furnish.valueOf(console.readLine().toUpperCase().trim());
                return furnish;
            } catch (IllegalArgumentException ex) {
                console.printCommandError("Введенная константа не представлена в допустимых значения Furnish");
            }
        }
    }

    /**
     * Метод, который читает поле view объекта Flat из потока, указанного в поле console.
     * Выводит список допустимых констант
     *
     * @return значение поля view, уже проверенное на условия допустимости
     */
    @Override
    public View readView() {
        View view;
        while (true) {
            try {
                console.printCommandText("Допустимые значения view:\n");
                for (View val : View.values()) {
                    console.printCommandText(val.name() + "\n");
                }
                console.printCommandText("view: ");
                String str = console.readLine().trim();
                if (str.equals("")) {
                    view = null;
                } else {
                    view = View.valueOf(str.toUpperCase().trim());
                }
                return view;
            } catch (IllegalArgumentException ex) {
                console.printCommandError("Введенная константа не представлена в допустимых значения View");
            }
        }
    }

    /**
     * Метод, который читает поля House.
     *
     * @return объект типа House
     */
    @Override
    public House readHouse() {
        while (true) {
            console.printCommandText("Нажмите enter, чтобы ввести в поле house null или введите House name (not null):");
            String str = console.readLine().trim();
            if (str.equals(""))
                return null;
            else {
                if (!str.matches(PATTERN_NAMES))
                    console.printCommandError("Введенная строка содержит запрещенные символы");
                else return new House(str, readHouseYear(), readHouseNumberOfFloors(),
                        readHouseNumberOfFlatsOnFloor(), readHouseNumberOfLifts());
            }
        }
    }

    /**
     * Метод, который читает поле name объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля area, уже проверенное на условия допустимости
     */
    @Override
    public String readHouseName() {
        String str;

        while (true) {
            console.printCommandText("House name (not null): ");
            str = console.readLine().trim();
            if (str.equals("")) console.printCommandError("Значение поля не может быть null");
            else if (!str.matches(PATTERN_NAMES))
                console.printCommandError("Введенная строка содержит запрещенные символы");
            else return str;
        }
    }

    /**
     * Метод, который читает поле year объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля year, уже проверенное на условия допустимости
     */
    @Override
    public int readHouseYear() {
        int houseYear;
        while (true) {
            try {
                console.printCommandText("houseYear(int & houseYear > 0): ");
                houseYear = Integer.parseInt(console.readLine().trim());
                if (houseYear <= 0) throw new InvalidValueException();
                else return houseYear;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение houseYear должно быть больше 0");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа int");
            }
        }
    }

    /**
     * Метод, который читает поле numberOfFloors объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля numberOfFloors, уже проверенное на условия допустимости
     */
    @Override
    public Long readHouseNumberOfFloors() {
        Long numberOfFloors;
        while (true) {
            try {
                console.printCommandText("number of floors(long & 0 < numberOfFloors <= 39): ");
                String str = console.readLine().trim();
                if (str.equals("")) numberOfFloors = null;
                else {
                    numberOfFloors = Long.parseLong(str);
                    if (numberOfFloors <= 0 || numberOfFloors > 39) throw new InvalidValueException();
                }
                return numberOfFloors;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение numberOfFloors должно быть больше 0 и не более 39");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа Long");
            }
        }
    }

    /**
     * Метод, который читает поле numberOfFlatsOnFloor объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля numberOfFlatsOnFloor, уже проверенное на условия допустимости
     */
    @Override
    public long readHouseNumberOfFlatsOnFloor() {
        long numberOfFlatsOnFloor;
        while (true) {
            try {
                console.printCommandText("number of flats on floor(long & numberOfFlatsOnFloor > 0): ");
                numberOfFlatsOnFloor = Long.parseLong(console.readLine().trim());
                if (numberOfFlatsOnFloor <= 0) throw new InvalidValueException();
                else return numberOfFlatsOnFloor;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение numberOfFlatsOnFloor должно быть больше 0");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа long");
            }
        }
    }

    /**
     * Метод, который читает поле numberOfLifts объекта Flat из потока, указанного в поле console.
     *
     * @return значение поля numberOfLifts, уже проверенное на условия допустимости
     */
    @Override
    public Long readHouseNumberOfLifts() {
        Long numberOfLifts;
        while (true) {
            try {
                console.printCommandText("number of lifts(long & numberOfLifts > 0): ");
                String str = console.readLine().trim();
                if (str.equals("")) numberOfLifts = null;
                else {
                    numberOfLifts = Long.parseLong(str);
                    if (numberOfLifts <= 0) throw new InvalidValueException();
                }
                return numberOfLifts;
            } catch (InvalidValueException ex) {
                console.printCommandTextNext("Значение numberOfLifts должно быть больше 0");
            } catch (NumberFormatException ex) {
                console.printCommandError("Число должно быть типа long");
            }
        }
    }
}
