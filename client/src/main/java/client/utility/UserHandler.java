package client.utility;

import common.data.Coordinates;
import common.data.Furnish;
import common.data.House;
import common.data.View;
import common.exceptions.ErrorInScriptException;
import common.interaction.FlatValue;
import common.interaction.requests.Request;
import common.interaction.responses.ResponseCode;
import common.utility.UserConsole;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

/**
 * Получает запросы пользователей
 */
public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private UserConsole userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(UserConsole userConsole) {
        this.userScanner = userConsole;
    }

    /**
     * Проверяет находиться ли обработчик в файловом режиме
     *
     * @return находиться ли обработчик в файловом режиме
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }

    /**
     * Генерирует Flat на обновление
     *
     * @return flat на обновление
     * @throws ErrorInScriptException когда что-то не так в скрипте
     */
    private FlatValue generateFlatUpdate() throws ErrorInScriptException {
        FlatReader flatReader = new FlatReader(userScanner);
        if (fileMode()) flatReader.setFileMode();
        String name = flatReader.askQuestion("Хотите поменять имя квартиры?") ?
                flatReader.readName() : null;
        Coordinates coordinates = flatReader.askQuestion("Хотите изменить координаты квартиры?") ?
                flatReader.readCoordinates() : null;
        int area = (flatReader.askQuestion("Хотите изменить площадь квартиры?") ?
                        flatReader.readArea() : -1);
        long numberOfRooms = (flatReader.askQuestion("Хотите изменить количество комнат?") ?
                flatReader.readNumberOfRooms() : -1);
        long numberOfBathrooms = (flatReader.askQuestion("Хотите изменить количество ванных комнат?") ?
                flatReader.readNumberOfBathrooms() : -1);
        Furnish furnish = (flatReader.askQuestion("Хотите изменить мебель в квартире?") ?
                flatReader.readFurnish() : null);
        View view = (flatReader.askQuestion("Хотите изменить вид из квартиры?") ?
                flatReader.readView() : null);
        House house = (flatReader.askQuestion("Хотите изменить мебель в квартире?") ?
                flatReader.readHouse() : null);
        return new FlatValue(
                name,
                coordinates,
                area,
                numberOfRooms,
                numberOfBathrooms,
                furnish,
                view,
                house
        );
    }

    /**
     * Генерирует Flat на добавление
     *
     * @return Flat на добавление
     */
    private FlatValue generateFlatAdd() throws ErrorInScriptException {
        FlatReader flatReader = new FlatReader(userScanner);
        if (fileMode()) flatReader.setFileMode();
        return new FlatValue(
                flatReader.readName(),
                flatReader.readCoordinates(),
                flatReader.readArea(),
                flatReader.readNumberOfRooms(),
                flatReader.readNumberOfBathrooms(),
                flatReader.readFurnish(),
                flatReader.readView(),
                flatReader.readHouse()
        );
    }
}
