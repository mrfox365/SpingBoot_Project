Інструкція з оновлення проєкту Classroom Management System

1. Підготовка до оновлення

1.1 Створення резервних копій
База даних:
pg_dump -U classroom_user -F c classroom > backup_classroom_$(date +%F).dump

JAR-файл, конфігурації, логи:
cp /opt/classroom-app/classroom-app.jar /opt/backups/
cp /etc/systemd/system/classroom-app.service /opt/backups/
cp -r /var/log/classroom-app /opt/backups/

1.2 Перевірка сумісності
Упевніться, що:
Нова версія коду сумісна з поточною версією СУБД.
Наявні конфігураційні файли (application.yml) мають всі потрібні ключі для нової версії.

1.3 Планування часу простою ( якщо потрібно )
Повідомити користувачів про короткочасне відключення.
Забронювати вікно часу, наприклад: з 21:00 до 21:30.

2. Процес оновлення

2.1 Зупинка потрібних служб
sudo systemctl stop classroom-app

2.2 Розгортання нового коду
git pull origin main
mvn clean package
cp target/classroom-app.jar /opt/classroom-app/classroom-app.jar

2.3 Міграція даних ( якщо потрібно )
psql -U classroom_user -d classroom -f docs/migration_vX.sql

2.4 Оновлення конфігурацій
Перевірити або оновити application.yml або application-prod.yml.

Перезапустити службу:
sudo systemctl daemon-reload
sudo systemctl start classroom-app

3. Відкат (Rollback) у разі невдалого оновлення

3.1 Зупинка нової версії
sudo systemctl stop classroom-app

3.2 Відновлення резервних копій
JAR-файл:
cp /opt/backups/classroom-app.jar /opt/classroom-app/classroom-app.jar

База даних:
pg_restore -U classroom_user -d classroom -c backup_classroom_YYYY-MM-DD.dump

3.3 Перезапуск старої версії
sudo systemctl start classroom-app

