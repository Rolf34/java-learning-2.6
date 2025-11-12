## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

---

Практична робота №5 — Використання статичних полів та методів у Java

Короткий опис змін (цей репозиторій):
- Додано глобальний статичний сховище `company.storage.CompanyDataStore` (статичні колекції для Employee та Project).
- Додано фабрику `company.ObjectFactory` з набором статичних методів для створення сутностей.
- Оновлено `company.storage.CompanyEntityManager` щоб використовувати `CompanyDataStore` (демонстрація статичного збереження).
- Додано статичний лічильник у `company.empoloyees.Employee` (`getEmployeeCount()`), що відстежує створені екземпляри.
- Оновлено `main.Main` для демонстрації роботи статичних методів/полів та реєстрації сутностей у сховищі.

Запуск (Windows cmd):
1. Скомпілювати всі .java файли у папку `bin` (команда створить тимчасовий файл зі списком джерел):

```cmd
if exist sources_quoted.txt del sources_quoted.txt & for /f "delims=" %i in ('dir /b /s src\*.java') do @echo "%i" >> sources_quoted.txt & javac -d bin @sources_quoted.txt
```

2. Запустити головний клас:

```cmd
java -cp bin main.Main
```

Файли, що були створені/змінені:
- `src/company/empoloyees/Employee.java` — додано статичний лічильник `employeeCount` і метод `getEmployeeCount()`.
- `src/company/storage/CompanyDataStore.java` — новий клас зі статичними Map для EMPLOYEES та PROJECTS.
- `src/company/storage/CompanyEntityManager.java` — переписано, щоб делегувати зберігання у `CompanyDataStore`.
- `src/company/ObjectFactory.java` — нова фабрика зі статичними методами створення об'єктів.
- `src/main/Main.java` — додано приклади реєстрації у сервісі/статичному сховищі і вивід лічильників.

Якщо потрібно, можу згенерувати шаблон звіту (Markdown або .docx), або PlantUML файл для оновлення діаграми класів.
