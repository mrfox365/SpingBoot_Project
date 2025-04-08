Classroom Management System

This program is a classroom management system designed for efficient management of student and course data.

Installation and Setup Instructions
To ensure proper functioning of the program, please follow these steps:

PostgreSQL Installation
Firstly, you need to install PostgreSQL. You can find the relevant instructions for your operating system on the official PostgreSQL website.

Database Creation
After installing PostgreSQL, create a local database named "classroom".
CREATE DATABASE classroom;

Connect to the Newly Created Database
Using the command line (\c classroom) or programming functionality.

Script Execution
Execute the script from Appendix 1 in the already created "classroom" database. This script contains the necessary table structure and initial data.

Spring Boot Maven Installation
Next, you need to install Spring Boot Maven. You can find documentation and instructions on the official Spring Boot website.

Project Download
Download the project from the GitHub repository. You can do this by cloning the repository or downloading the archive file.

Program Launch
After downloading the project, to run the program, execute the ProgApplication.java file. It is located at src\main\java\com\jamavcode\prog.

Note: Make sure your PostgreSQL database is running before launching the program.

🚧 Стандарти та правила документування коду

Щоб підтримувати чисту, зрозумілу і послідовну базу коду, кожен учасник повинен дотримуватись таких **вимог до документування**:

1. Javadoc для класів та методів

- Обов'язково додавай `Javadoc` для **публічних класів**, **методів**, і **інтерфейсів**.
- Коротко описуй призначення та параметри.
- Приклад:

```java
/**
 * Сервіс для обробки запитів користувача.
 */
public class UserService {

    /**
     * Знаходить користувача за ID.
     *
     * @param id ID користувача
     * @return об'єкт користувача
     * @throws UserNotFoundException якщо користувача не знайдено
     */
    public User getUserById(Long id) { ... }
}

2. Коментарі в коді
Коментуй лише нелогічні або складні місця в коді.

Уникай надлишкових коментарів типу // інкремент.

// Перевірка токена перед виконанням запиту
if (!authService.isTokenValid(token)) {
    throw new UnauthorizedException();
}

