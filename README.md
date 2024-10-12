# Мобильное Android-приложение для студии танцев
Проект выполнен в рамках учебной проектной деятельности в Университете ИТМО. 
Приложение решает реальную проблему, связанную с неудобством пользователя записываться на секции, смотреть расписание и количество свободных мест. Все данные предоставляются сервером, но в том числе хранятся локально (после первой авторизации), так что при отсутствии изменений в расписании оно будет доступлно оффлайн.
На данный момент проект находится в процессе разработки.

## Клиент и Сервер
Данный репозиторий является Android-клиентом разрабатываемого приложения.
Ссылка на репозиторий с сервером - https://github.com/vovanshil95/DanceClubBackend.

## Содержание
- [Технологии](#технологии)
- [Использование](#использование)
- [Deploy и CI/CD](#deploy-и-cicd)
- [Команда проекта](#команда-проекта)

## Технологии
- Android SDK
- Kotlin
- Gradle (Kotlin DSL)
- MVVM
- Jetpack Compose
- Material Design
- Room (SQLite)
- Retrofit2 + Okhttp (REST API)
- Kotlin Serialization
- Kotlin Coroutines

## Использование

### Скачайте установочный файл приложения
Скачайте последнюю версию [релиза](releases/latest) в формате APK. Ваш телефон должен иметь версию ОС Android 9 и старше. 

### Разрабатывайте
Необходимые для работы проекта библиотеки и их версии перечислены в [файле версий toml](gradle/libs.versions.toml).
Основное:
- Android API 28+ (Android 9 (P))
- Kotlin 1.9.24
- AGP 8.7.0
- Jetpack Compose

## Deploy и CI/CD
После каждого commit или подтверждённого pull request в мастер ветку GitHub автоматически соберет APK, который будет находиться в артефактах GitHub Actions.

## Команда проекта
- Android
  - @TrombBone - Team Lead & Android-developer
  - @vladryanka - Android-developer
- Server (Python)
  - @vovanshil95 - Backend-developer
  - @P0linaria - Documentarian & Backend-developer
