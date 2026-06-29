# Philharmonic Project

Учебный проект по курсы БД. Система управления филармонией – бэкенд на Spring Boot, и клиентское приложение на JavaFX.

## Структура проекта

- `Philharmonic/` – серверная часть (Spring Boot + JPA + Security)
- `philharmonic-client/` – клиентская часть (JavaFX)

## Требования

- **Java 25**
- Maven 3.9+
- Docker / Docker-compose

## Запуск сервера

### 1. База данных

Создайте базу данных `philharmonic`:

```sql
CREATE DATABASE philharmonic;
```

Или используйте Docker:

```bash
docker run --name postgres -e POSTGRES_DB=philharmonic -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres:18
```

### 2. Конфигурация

По умолчанию сервер использует профиль **dev** со следующими настройками (заданы в `application-dev.yml`):

- БД: `jdbc:postgresql://localhost:5432/philharmonic`, пользователь `user`, пароль `pass`

### 3. Запуск через Docker

#### Режим разработки (dev)

```bash
cd Philharmonic
docker compose up
```

#### Режим продакшена (prod)

Создайте в корне сервера файл `.env` (или задайте переменные окружения):

```env
DB_URL=jdbc:postgresql://postgres:5432/philharmonic
DB_USER=prod_user
DB_PASSWORD=prod_password
JWT_SECRET=supersecretproductionkey
```

Запустите:

```bash
docker compose -f docker-compose.prod.yml --env-file .env up
```

## Запуск клиента

Клиент написан на JavaFX и взаимодействует с сервером через REST API.

### 1. Сборка и запуск

```bash
cd philharmonic-client
./mvnw javafx:run
```

Приложение откроет окно входа. По умолчанию клиент подключается к `http://localhost:8080`. При необходимости адрес сервера можно переопределить параметром JVM:

```bash
./mvnw javafx:run -Dapi.baseUrl=http://your-server:8080
```

### 2. Сборка JAR

```bash
./mvnw clean package
```

Затем запустите:

```bash
java -jar target/philharmonic-client-1.0.0-SNAPSHOT.jar
```

## Начальные данные

При первом запуске сервер автоматически создаёт таблицы (Hibernate `ddl-auto=update`) и заполняет роли и разрешения.

Или можно использовать SQL скрипты из `src/main/resources` для создания схемы с триггерами и стартовых стандартных данных пользователей

Стандартные роли:

- `USER` – только чтение (permission: `READ`)
- `ADMIN` – создание и редактирование (`CREATE`, `READ`, `UPDATE`)
- `SUPERADMIN` – полный доступ (`CREATE`, `READ`, `UPDATE`, `DELETE`)

- Пагинация: на сервере планируется добавить поддержку `Pageable` для всех GET-эндпоинтов (в текущей версии пока возвращаются все записи).
- Клиент использует **AtlantaFX** для оформления, все стили в `src/main/resources/css/app.css`.

Суперадминистратор может создавать пользователей и назначать роли через интерфейс клиента или напрямую через API.

Если такого нет, вы можете зарегистрироваться через endpoint `/auth/register` (доступен без авторизации) или вставить запись в БД вручную.

## API Contracts

Подробная документация API доступна в файле [`curl-examples.md`](./Philharmonic/curl-examples.md). Основные группы эндпоинтов:

- `/api/artists` – артисты
- `/api/venues` – площадки (с наследованием типов)
- `/api/events` – мероприятия (с наследованием типов)
- `/api/impresarios`, `/api/organizers`, `/api/genres`
- `/api/competitions`, `/api/competition-results`
- `/api/users`, `/api/roles` (только для SUPERADMIN)
- `/auth/login`, `/auth/register`

**Авторизация** основана на разрешениях (permissions). Методы контроллеров используют аннотацию `@RequirePermission("CREATE")` и т.д. Все эндпоинты (кроме `/auth/**`) требуют JWT токена в заголовке `Authorization: Bearer <token>`.

## Переменные окружения

Для продакшена обязательно задайте:

| Переменная | Описание |
|------------|----------|
| `DB_URL`   | URL подключения к БД |
| `DB_USER`  | Пользователь БД |
| `DB_PASSWORD` | Пароль БД |
| `JWT_SECRET` | Секретный ключ для подписи JWT (минимум 32 символа) |

## Лицензия

Проект разработан исключительно в учебных целях.