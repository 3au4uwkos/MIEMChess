# MIEMCHESS
*Unleashing Strategic Brilliance in Every Move*

![last-commit](https://img.shields.io/github/last-commit/3au4uwkos/MIEMChess?style=flat&logo=git&logoColor=white&color=0080ff)
![repo-top-language](https://img.shields.io/github/languages/top/3au4uwkos/MIEMChess?style=flat&color=0080ff)
![repo-language-count](https://img.shields.io/github/languages/count/3au4uwkos/MIEMChess?style=flat&color=0080ff)

**Built with:**

![JSON](https://img.shields.io/badge/JSON-000000.svg?style=flat&logo=JSON&logoColor=white)
![Markdown](https://img.shields.io/badge/Markdown-000000.svg?style=flat&logo=Markdown&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-000000.svg?style=flat&logo=Spring&logoColor=white)
![npm](https://img.shields.io/badge/npm-CB3837.svg?style=flat&logo=npm&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=flat&logo=JavaScript&logoColor=black)  
![React](https://img.shields.io/badge/React-61DAFB.svg?style=flat&logo=React&logoColor=black)
![XML](https://img.shields.io/badge/XML-005FAD.svg?style=flat&logo=XML&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=flat&logo=PostgreSQL&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4.svg?style=flat&logo=Axios&logoColor=white)

---

## Table of Contents
1. [Overview](#overview)  
2. [Features](#features)  
3. [Getting Started](#getting-started)  
   - [Prerequisites](#prerequisites)  
   - [Installation](#installation)  
   - [Usage](#usage)  
   - [Testing](#testing)  
4. [Project Structure](#project-structure)  
5. [Contributing](#contributing)  
6. [License](#license)  
7. [Contact](#contact)  

---

## Overview
**MIEMChess** — это open-source фреймворк full-stack для создания масштабируемых и сверхбыстрых многопользовательских шахматных игр в режиме реального времени. Реализован на реактивном Spring Boot с WebFlux, WebSocket и фронтендом на React для плавного UX.

---

## Features
- 🧩 **Реактивная архитектура:** WebFlux + WebSocket для низкой задержки и двунаправленной связи, обеспечивающей плавную игру.  
- 🔒 **Безопасность:** аутентификация с JWT, управление пользователями и разграничение доступа по ролям.  
- 🎮 **Игровая логика:** модульные классы фигур, валидация ходов, управление состоянием игры.  
- 🌐 **Full-Stack интеграция:** инфраструктура backend + React UI для динамичных обновлений в реальном времени.  
- 🤝 **Матчмейкинг и мультиплеер:** подбор игроков, хостинг игры и live-обновления игрового процесса.

---

## Getting Started

### Prerequisites
- **Java**  
- **Maven**  
- **Node.js + npm**

### Installation

```bash
# Клонировать репозиторий
git clone https://github.com/3au4uwkos/MIEMChess.git
cd MIEMChess
````

Установить зависимости:

**С Maven:**

```bash
mvn install
```

**С npm (Frontend):**

```bash
cd frontend
npm install
```

### Usage

Запустить backend:

```bash
cd path/to/backend
mvn exec:java
```

Запустить frontend:

```bash
cd frontend
npm start
```

---

## Testing

Запуск тестов:

**С Maven:**

```bash
mvn test
```

**С npm:**

```bash
cd frontend
npm test
```

---

## Project Structure

```text
MIEMChess/
├── backend/         # Spring Boot + reactive backend
├── frontend/        # React-based UI
├── README.md
├── pom.xml          # Maven config
└── package.json     # npm/front-end config
```

---

## Contributing

1. Сделай форк проекта
2. Создай свою ветку: `git checkout -b feature/my-feature`
3. Внеси изменения и закомить: `git commit -am 'Add some feature'`
4. Запушь ветку и отправь Pull Request

---

## License

Этот проект лицензируется под **MIT License**. Подробности в `LICENSE` файле.

---

## Contact

Доступен для вопросов, обратной связи и предложений. Свяжись в GitHub или оставь issue на странице репозитория.

