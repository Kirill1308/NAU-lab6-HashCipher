package com.nau.lab6;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    public String readFromFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                // Додаємо перенос рядка, якщо це не останній рядок
                if (reader.ready()) {
                    content.append("\n");
                }
            }
        }

        return content.toString();
    }

    public void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            writer.write(content);
        }
    }

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }
}
