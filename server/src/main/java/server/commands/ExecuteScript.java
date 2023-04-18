package server.commands;

import common.exceptions.RecursiveException;
import common.exceptions.WrongArgumentException;
import common.utility.Console;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс команды, которая считывает и исполняет скрипт из указанного файла
 */
public class ExecuteScript implements Command {

    private final CommandManager commandManager;
    private final Console console;

    private final LinkedList<String> scriptPathsSequence = new LinkedList<>(); //используется для обнаружения рекурсии
    private final LinkedList<Script> scriptSequence = new LinkedList<>(); //используется для работы со вложенными скриптами

    private void addScriptToList(String name, Script script) {
        scriptPathsSequence.add(name);
        scriptSequence.push(script);
    }

    private void removeScriptFromList() {
        scriptPathsSequence.remove();
        scriptSequence.remove();
    }

    /**
     * Конструктор класса
     *
     * @param commandManager Хранит ссылку на объект CommandManager
     */
    public ExecuteScript(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }

    //для каждого нового вызванного скрипта создается объект данного класса
    private class Script {
        private File file;
        private Scanner scanner;

        private void addFile(String path) throws IOException {
            file = new File(path);
            if (!file.canRead() || file.isDirectory() || !file.isFile()) throw new IOException();

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            scanner = new Scanner(bufferedInputStream);
        }

        private void run() {
            addScriptToList(file.getPath(), this);
            try {
                console.printCommandTextNext("Выполнение скрипта " + file.getName());
                while (scanner.hasNext()) {
                    commandManager.nextCommand();
                }
                console.printCommandTextNext("Скрипт " + file.getName() + " выполнен");

            } catch (Exception e) {
                console.printCommandTextNext("Выполнение скрипта " + file.getName() + " прервано.");
            } finally {
                removeScriptFromList();
            }
        }
    }

    /**
     * Метод исполняющий команду.
     *
     * @param args Строка, содержащая переданные команде аргументы.
     */
    @Override
    public void execute(String args) throws WrongArgumentException {
        if (args.isEmpty()) throw new WrongArgumentException();

        console.turnOffScriptMode();
        Script script = new Script();
        try {
            script.addFile(args);
            if (scriptPathsSequence.contains(args)) throw new RecursiveException();
            console.turnOnScriptMode(script.scanner);
            script.run();

        } catch (IOException e) {
            console.printCommandError("ошибка при добавлении файла \"" + args + "\"");
        } catch (RecursiveException e) {
            console.printCommandError("произошла рекурсия: \n\tСкрипт \"" + args + "\" не будет выполнен.");
        }

        if (scriptSequence.size() == 0) console.turnOffScriptMode();
        else console.turnOnScriptMode(scriptSequence.peek().scanner); //обновляет scanner
    }

    /**
     * @return Описание команды execute_script
     * @see Command
     */
    @Override
    public String getDescription() {
        return "выполняет команды, описанные в скрипте";
    }
}