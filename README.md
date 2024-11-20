# Сервис для управления задачами

## Возможности

* ✅ Swagger UI: http://localhost:8080/swagger-ui/index.html
* ✅ Регистрация, авторизация по JWT
* ✅ Изменение статуса и комментирование исполняемых задач для обычных пользователей
* ✅ Создание, изменение, удаление, комментирование любых задач для админов
* ✅ Валидация входных запросов
* ✅ Пагинация, фильтрация выхода
* ✅ Вывод ошибок (статус и JSON объект с сообщением)
* ✅ Интеграционные тесты

## Технологии

* Java 17, Spring, Spring Security, Spring Validation, Spring JPA, MySQL, Lombok, Flyway, MapStruct, OpenAPI, Testcontainers, JUnit

## Локальный запуск (для IDE)

1. `docker-compose up`
2. Запустить приложение EffectiveMobileTestApplication в IDE

## Production запуск

1. Указать переменные окружения DB_NAME, DB_USER, DB_PASSWORD, JWT_SECRET, JWT_EXPIRES_IN
2. `docker-compose -f docker-compose-prod.yml up`
3. Запустить EffectiveMobileTestApplication в профиле prod