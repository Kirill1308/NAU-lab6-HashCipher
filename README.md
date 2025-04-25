# Програма Однонаправленої Геш-Функції SHA-1

## Створення геш-значення для тексту

![image](https://github.com/user-attachments/assets/7c87d352-1a1c-4091-b13e-70ccb8090c9a)

**Параметри:**
- 1
- Я, Попов Кирил Олександрович студент університету
- так
- hash_text.txt

## Створення геш-значення для файлу

![image](https://github.com/user-attachments/assets/b50371f9-e825-47e4-9433-d96828f2e614)

**Параметри:**
- 2
- input.txt
- так
- hash_file.txt

## Перевірка цілісності тексту (успішна)

![image](https://github.com/user-attachments/assets/f1c134be-e61c-46e2-a844-f67d32fd3b1e)

**Параметри:**
- 3
- Я, Попов Кирил Олександрович студент університету
- 2ed24f92c7bc9a7f5539144e38a63077199ba12c

## Перевірка цілісності тексту (порушена)

![image](https://github.com/user-attachments/assets/89ac6e7a-b737-4aee-9a9e-137cf228a3c5)

**Параметри:**
- 3
- Я, Попов Кирил студент університету
- 2ef7bde608ce5404e97d5f042f95f89f1c232871

## Перевірка цілісності файлу (успішна)

![image](https://github.com/user-attachments/assets/d1673aef-a817-4d69-b222-5529f125ac1f)

**Параметри:**
- 4
- input.txt
- hash_file.txt
