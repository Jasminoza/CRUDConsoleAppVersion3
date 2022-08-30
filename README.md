## **Задача**: 
##### Необходимо реализовать консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:

* ###### Developer (id, firstName, lastName, List<Skill> skills, Specialty specialty)
* ###### Skill
* ###### Specialty
* ###### Status (enum ACTIVE, DELETED)

Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.

##### Требования:

1. ###### Все CRUD операции для каждой из сущностей
2. ###### Придерживаться подхода MVC
3. ###### Для сборки проекта использовать Maven
4. ###### Для взаимодействия с БД - Hibernate
5. ###### Для конфигурирования Hibernate - аннотации
6. ###### Инициализация БД должна быть реализована с помощью flyway
7. ###### Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito)

##### Для запуска приложения необходимо иметь настроенный MySQL сервер, и создать базу данных. В классе ConnectionToMySQL актуализировать данные БД, логина и пароля.
## Технологии: Java, PostgreSQL, Hibernate, Flyway, Maven.