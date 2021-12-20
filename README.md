Для первого запуска БД в терминале Docker выполнить команду:
docker run --name postgresdb14  -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=0 -e POSTGRES_DB=postgres -d postgres:14.0

Для повторного запуска БД использовать команду:
docker start postgresdb14


Тестовые аккаунты:

User: 
user@gmail.com password: user

Admin: 
admin@gmail.com password: admin

Moderator: 
moderator@gmail.com password: moderator
---

