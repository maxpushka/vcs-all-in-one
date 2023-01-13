# Програмні залежності

Для використання програми мають бути встановлені наступні програмні пакети:
- Java 19 (OpenJDK)
- Git
- Mercurial
- Apache Subversion

# Запуск проекту

## Linux та MacOS

Виконайте команду `make build install` у терміналі
Після завершення компіляції у директорії `/usr/local/bin` зявисться утиліта `vcs`.

Використання програми: `vcs <command> <arguments>`

## Windows

Виконайте команду `.\gradlew.bat uberJar` у терміналі
Після завершення компіляції JAR-файл буде знаходитись по шляху `app\build\libs\app-uber.jar`

Використання програми: `java -jar <шлях до репозиторію>\app\build\libs\app-uber.jar <command> <arguments>`

