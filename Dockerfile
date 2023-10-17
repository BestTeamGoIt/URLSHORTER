FROM openjdk:17

# Встановлюємо робочий каталог в контейнері
WORKDIR /app

# Копіюємо JAR файл додатка в контейнер
COPY ./build/libs/URLSHORTER-plain.jar /app/URLSHORTER-plain.jar

# команда для запуску додатка
CMD ["java", "-jar", "URLSHORTER-plain.jar"]
