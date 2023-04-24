package common.utility;

import common.data.Flat;
import common.data.View;
import common.exceptions.InvalidValueException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

/**
 * Класс отвечающий за работу с коллекциями
 */
public class CollectionManager {

    // Коллекция, с которой осуществляется работа
    private Hashtable<Integer, Flat> hashtable;

    private static final HashSet<Integer> allId = new HashSet<>();
    private Console console;
    // Время инициализации коллекции
    private LocalDateTime collectionInitialization;

    private FlatReader flatReader;

    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    /**
     * Максимальный ID у объектов коллекции
     */
    public static final int MAX_ID = 100000;

    /**
     * Конструктор, создающий новый объект менеджера коллекции
     */
    public CollectionManager(Console console, Hashtable<Integer, Flat> hashtable, FlatReader flatReader) {
        if (hashtable != null) this.hashtable = hashtable;
        else this.hashtable = new Hashtable<>();
        this.console = console;
        this.flatReader = flatReader;

        String i = LocalDateTime.now().toString();
        collectionInitialization = LocalDateTime.parse(i);

        for (Flat flat : this.hashtable.values()){
            allId.add(flat.getId());
        }
    }

    public CollectionManager(FileManager fileManager, Hashtable<Integer, Flat> hashtable) {
        if (hashtable != null) this.hashtable = hashtable;
        else this.hashtable = new Hashtable<>();

        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;

        for (Flat flat : this.hashtable.values()){
            allId.add(flat.getId());
        }
    }

    /**
     * Метод возвращает коллекцию целиком
     * @return коллекция
     */
    public Hashtable<Integer, Flat> getCollection(){
        return hashtable;
    }

    /**
     * Метод выводит информацию о коллекции
     */
    public void info() {
        console.printCommandTextNext("Коллекция: " + hashtable.getClass().getSimpleName());
        console.printCommandTextNext("Тип элементов коллекции: " + Flat.class.getSimpleName());
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateTimeFormatter europeanDateFormat = DateTimeFormatter.ofPattern(pattern);
        console.printCommandTextNext("Время инициализации коллекции: " + collectionInitialization.format(europeanDateFormat));
        console.printCommandTextNext("Количество элементов в коллекции: " + hashtable.size());
    }

    /**
     * Метод, добавляющий новый элемент в коллекцию
     *
     * @param key   идентификатор элемента
     * @param flat элемент коллекции, который нужно добавить
     */
    public void insert(Integer key, Flat flat) {
        if (!hashtable.contains(key)) {
            hashtable.put(key, flat);
            allId.add(flat.getId());
        } else console.printCommandTextNext("Элемент с данным ключом уже существует");
    }

    /**
     * Метод возвращает ключ элемента по его id
     *
     * @param id id
     * @return ключ
     */
    public int getKey(int id){
        for (int key : hashtable.keySet()){
            if (hashtable.get(key).getId() == id) {
                return key;
            }
        }
        return -1;
    }
    /**
     * Метод, изменяющий поле выбранного элемента коллекции
     *
     * @param key идентификатор
     * @param field имя поля
     */
    public void update(int key, String field) throws InvalidValueException {

        switch (field) {
            case "name": {
                hashtable.get(key).setName(flatReader.readName());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "coordinate_x": {
                hashtable.get(key).setCoordinateX(flatReader.readCoordinatesX());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "coordinate_y": {
                hashtable.get(key).setCoordinateY(flatReader.readCoordinatesY());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "area": {
                hashtable.get(key).setArea(flatReader.readArea());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "number_of_rooms": {
                hashtable.get(key).setNumberOfRooms(flatReader.readNumberOfRooms());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "number_of_bathrooms": {
                hashtable.get(key).setNumberOfBathrooms(flatReader.readNumberOfBathrooms());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "furnish": {
                hashtable.get(key).setFurnish(flatReader.readFurnish());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "view": {
                hashtable.get(key).setView(flatReader.readView());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "house_name": {
                hashtable.get(key).setHouseName(flatReader.readHouseName());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "house_year": {
                hashtable.get(key).setHouseYear(flatReader.readHouseYear());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "house_number_of_floors": {
                hashtable.get(key).setHouseNumberOfFloors(flatReader.readHouseNumberOfFloors());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "house_number_of_flats_on_floor": {
                hashtable.get(key).setHouseNumberOfFlatsOnFloor(flatReader.readHouseNumberOfFlatsOnFloor());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "house_number_of_lifts": {
                hashtable.get(key).setHouseNumberOfLifts(flatReader.readHouseNumberOfLifts());
                console.printCommandTextNext("Значение поля было изменено");
                break;
            }
            case "stop": {
                break;
            }
            default: {
                throw new InvalidValueException();
            }
        }
    }

    /**
     * Метод, удаляющий выбранный по идентификатору элемент коллекции
     *
     * @param key идентификатор элемента коллекции (ключ)
     */
    public void removeKey(Integer key) {
        Flat flat = hashtable.remove(key);
        allId.remove(flat.getId());
    }

    /**
     * Метод, удаляющий все элементы коллекции, значение ключа которых меньше указанного
     *
     * @param key значение ключа, меньше которого следует удалять элементы
     */
    public void removeLowerKey(Integer key) {
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, Flat> entry : hashtable.entrySet()) {
            if (entry.getKey() < key) keys.add(entry.getKey());
        }
        for (Integer k : keys) {
            removeKey(k);
        }
    }

    /**
     * Метод, удаляющий все элементы коллекции, значение ключа которых больше указанного
     *
     * @param key значение ключа, больше которого следует удалять элементы
     */
    public void removeGreaterKey(Integer key) {
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, Flat> entry : hashtable.entrySet()) {
            if (entry.getKey() > key) keys.add(entry.getKey());
        }
        for (Integer k : keys) {
            removeKey(k);
        }
    }

    /**
     * Метод, удаляющий все элементы коллекции
     */
    public void clear() {
        hashtable.clear();
        allId.clear();
    }

    /**
     * Метод, удаляющий все элементы коллекции, вид которого соответствует заданному
     *
     * @param view выбранный вид элемента коллекции
     */
    public void removeAllByView(View view) {
        int size = hashtable.size();
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, Flat> entry : hashtable.entrySet()) {
            if (entry.getValue().getView() == null) keys.add(entry.getKey());
            else if (entry.getValue().getView().equals(view)) keys.add((entry.getKey()));
        }
        for (Integer key : keys) {
            hashtable.remove(key);
        }
        if (size == hashtable.size()) console.printCommandTextNext("Не было найдено элементов с таким значением поля");
        else console.printCommandTextNext("Элементы с данным значением поля удалены");
    }

    /**
     * Метод, выводящий истину, если в коллекции существует элемент с выбранным ключом, иначе ложь
     *
     * @param key идентификатор элемента (ключ)
     * @return true - в коллекции существует элемент с выбранным ключом, false - такого элемента не существует
     */
    public boolean containsKey(int key) {
        return hashtable.containsKey(key);
    }

    /**
     * Метод генерирует уникальное значение id
     *
     * @return уникальный id
     */
    public static int generateId(){
        int id;
        do {
            id = (int) (MAX_ID * Math.random() + 1);
        }while (allId.contains(id));
        allId.add(id);
        return id;
    }
}
