package com.nau.lab6;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class SHA1CommandLineRunner implements CommandLineRunner {

    private final HashService hashService;
    private final FileService fileService;

    public SHA1CommandLineRunner(HashService hashService, FileService fileService) {
        this.hashService = hashService;
        this.fileService = fileService;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Лабораторна робота №6 - ОДНОНАПРАВЛЕНІ ГЕШ-ФУНКЦІЇ");
            System.out.println("Виберіть операцію:");
            System.out.println("1. Створення геш-значення для тексту");
            System.out.println("2. Створення геш-значення для файлу");
            System.out.println("3. Перевірка цілісності тексту");
            System.out.println("4. Перевірка цілісності файлу");
            System.out.println("5. Вийти");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createHashForText(scanner);
                    break;
                case 2:
                    createHashForFile(scanner);
                    break;
                case 3:
                    verifyTextIntegrity(scanner);
                    break;
                case 4:
                    verifyFileIntegrity(scanner);
                    break;
                case 5:
                    System.out.println("Завершення роботи програми...");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createHashForText(Scanner scanner) {
        System.out.println("Введіть текст для обчислення геш-значення:");
        String text = scanner.nextLine();

        String hashValue = hashService.generateSHA1Hash(text);

        System.out.println("SHA-1 геш-значення:");
        System.out.println(hashValue);

        System.out.println("Бажаєте зберегти геш-значення у файл? (так/ні)");
        String saveChoice = scanner.nextLine();

        if (saveChoice.equalsIgnoreCase("так")) {
            System.out.println("Введіть ім'я файлу для збереження:");
            String fileName = scanner.nextLine();

            try {
                fileService.writeToFile(fileName, hashValue);
                System.out.println("Геш-значення успішно збережено у файл: " + fileName);
            } catch (Exception e) {
                System.err.println("Помилка при збереженні у файл: " + e.getMessage());
            }
        }
    }

    private void createHashForFile(Scanner scanner) {
        System.out.println("Введіть ім'я файлу для обчислення геш-значення:");
        String fileName = scanner.nextLine();

        try {
            String fileContent = fileService.readFromFile(fileName);
            String hashValue = hashService.generateSHA1Hash(fileContent);

            System.out.println("SHA-1 геш-значення для файлу " + fileName + ":");
            System.out.println(hashValue);

            System.out.println("Бажаєте зберегти геш-значення у файл? (так/ні)");
            String saveChoice = scanner.nextLine();

            if (saveChoice.equalsIgnoreCase("так")) {
                System.out.println("Введіть ім'я файлу для збереження геш-значення:");
                String hashFileName = scanner.nextLine();

                fileService.writeToFile(hashFileName, hashValue);
                System.out.println("Геш-значення успішно збережено у файл: " + hashFileName);
            }
        } catch (Exception e) {
            System.err.println("Помилка при читанні файлу або обчисленні геш-значення: " + e.getMessage());
        }
    }

    private void verifyTextIntegrity(Scanner scanner) {
        System.out.println("Введіть оригінальний текст:");
        String originalText = scanner.nextLine();

        System.out.println("Введіть геш-значення для перевірки:");
        String hashToCheck = scanner.nextLine();

        String calculatedHash = hashService.generateSHA1Hash(originalText);

        boolean isValid = calculatedHash.equalsIgnoreCase(hashToCheck);

        System.out.println("Результат перевірки цілісності:");
        if (isValid) {
            System.out.println("Цілісність підтверджена. Геш-значення співпадають.");
        } else {
            System.out.println("Цілісність порушена! Геш-значення не співпадають.");
            System.out.println("Обчислене геш-значення: " + calculatedHash);
            System.out.println("Надане геш-значення: " + hashToCheck);
        }
    }

    private void verifyFileIntegrity(Scanner scanner) {
        System.out.println("Введіть ім'я файлу для перевірки:");
        String fileName = scanner.nextLine();

        System.out.println("Введіть геш-значення для перевірки або ім'я файлу з геш-значенням:");
        String hashInput = scanner.nextLine();

        try {
            String fileContent = fileService.readFromFile(fileName);
            String calculatedHash = hashService.generateSHA1Hash(fileContent);

            String hashToCheck;
            if (hashInput.length() == 40 && hashInput.matches("[0-9a-fA-F]+")) {
                // Якщо введене значення схоже на SHA-1 геш (40 символів в шістнадцятковому форматі)
                hashToCheck = hashInput;
            } else {
                // Інакше припускаємо, що це ім'я файлу з геш-значенням
                hashToCheck = fileService.readFromFile(hashInput);
            }

            boolean isValid = calculatedHash.equalsIgnoreCase(hashToCheck);

            System.out.println("Результат перевірки цілісності файлу " + fileName + ":");
            if (isValid) {
                System.out.println("Цілісність підтверджена. Геш-значення співпадають.");
            } else {
                System.out.println("Цілісність порушена! Геш-значення не співпадають.");
                System.out.println("Обчислене геш-значення: " + calculatedHash);
                System.out.println("Надане геш-значення: " + hashToCheck);
            }
        } catch (Exception e) {
            System.err.println("Помилка при перевірці цілісності файлу: " + e.getMessage());
        }
    }
}
