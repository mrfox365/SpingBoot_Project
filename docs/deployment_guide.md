Інструкція з розгортання проекту у production-середовищі

1. Вимоги до апаратного забезпечення

Архітектура: x86_64 або ARM64
CPU: мінімум 2 ядра (4+ рекомендовано)
RAM: мінімум 4 ГБ (8 ГБ рекомендовано)
Диск: щонайменше 10 ГБ SSD

2. Необхідне програмне забезпечення

Java 17+ (JDK)
Apache Maven
PostgreSQL (13+)
Git
Apache
ufw (для брандмауера)

3. Налаштування мережі

Відкрити порти:
80, 443 (для HTTP/HTTPS)
5432 (локально або VPN)
Налаштувати DNS та SSL (Let's Encrypt)

4. Конфігурація серверів

Створити користувача appuser
Створити шлях /opt/classroom-app для jar
Логи: /var/log/classroom-app/app.log

5. Налаштування СУБД

Встановити PostgreSQL:
sudo apt install postgresql postgresql-contrib

Створити базу та користувача:
CREATE DATABASE classroom;
CREATE USER classroom_user WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE classroom TO classroom_user;

Скрипт ініціалізації бази: script.sql

6. Розгортання коду

git clone https://github.com/mrfox365/SpingBoot_Project.git
cd SpingBoot_Project
mvn clean package
cp target/*.jar /opt/classroom-app/classroom-app.jar

Створити systemd сервіс /etc/systemd/system/classroom-app.service:

[Unit]
Description=Classroom Management App
After=network.target

[Service]
User=appuser
ExecStart=/usr/bin/java -jar /opt/classroom-app/classroom-app.jar
SuccessExitStatus=143
StandardOutput=file:/var/log/classroom-app/app.log
Restart=on-failure

[Install]
WantedBy=multi-user.target

sudo systemctl daemon-reexec
sudo systemctl enable classroom-app
sudo systemctl start classroom-app

7. Перевірка працездатності

Логи:
tail -f /var/log/classroom-app/app.log

Тест API:
curl http://localhost:8080/api/students
Перевірка UI/публічної сторінки

