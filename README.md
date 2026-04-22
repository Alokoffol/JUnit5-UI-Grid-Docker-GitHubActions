# JUnit5-UI-Grid-Docker-GitHubActions

## Описание

UI тесты интернет-магазина Saucedemo с использованием **JUnit 5**, **Selenium Grid** в **Docker** и **GitHub Actions**.

Проект демонстрирует:
- ✅ JUnit 5 параметризованные тесты
- ✅ Selenium Grid в Docker (Chrome + Firefox)
- ✅ Параллельный запуск тестов
- ✅ GitHub Actions CI/CD
- ✅ Allure отчеты

## Технологии

| Технология | Назначение |
|------------|------------|
| Java 17 | Язык программирования |
| JUnit 5 | Фреймворк для тестирования |
| Selenium Grid | Удалённый запуск браузеров |
| Docker | Контейнеризация Grid |
| GitHub Actions | CI/CD |
| Allure | Отчеты о тестировании |

## Запуск тестов локально

```bash
# Поднять Selenium Grid
docker compose up -d

# Запустить тесты
mvn clean test -DuseGrid=true -Dbrowser=chrome
```

## Структура проекта
```
src/test/java/
├── base/TestBase.java
├── constants/AppConstants.java
├── driver/
│   ├── ChromeOptionsConfig.java
│   ├── LocalDriverFactory.java
│   └── RemoteDriverFactory.java
├── pages/
│   ├── BasePage.java
│   ├── CartPage.java
│   ├── CheckoutPage.java
│   ├── LoginPage.java
│   └── ProductsPage.java
├── resources/
│   └── junit-platform.properties
└── tests/
├── CartTests.java
├── LoginTests.java
└── PurchaseTest.java
```