# Online Examination System

A Java Swing desktop application for conducting online exams with user authentication, profile management, timed MCQ tests, and result evaluation.

## Features

- User registration and login
- Profile dashboard showing user details
- Question-based exam flow with multiple-choice questions
- Countdown timer for each exam
- Automatic score calculation
- Result summary after submission
- Local data persistence for users

## Project structure

```text
OnlineExaminationSystem/
├── src/
│   └── com/exam/
│       ├── data/
│       ├── model/
│       ├── service/
│       ├── ui/
│       ├── util/
│       └── Main.java
├── data/
│   └── users.dat
├── out/
├── README.md
└── java_files.txt
```

## Prerequisites

- Java JDK 17 or above
- Command Prompt / Terminal

## Default login users

The app comes with pre-seeded users for quick testing:

- Username: `admin` / Password: `admin123`
- Username: `student` / Password: `student123`
- Username: `neyash` / Password: `123456`

## Run the project

From the project root:

```bash
cd "D:\Oasis Infobyte\Online Examination System\OnlineExaminationSystem"
"C:\Program Files\Java\jdk-25\bin\javac.exe" -d out src\com\exam\Main.java src\com\exam\data\QuestionBank.java src\com\exam\data\UserDatabase.java src\com\exam\model\Question.java src\com\exam\model\Result.java src\com\exam\model\User.java src\com\exam\service\AuthService.java src\com\exam\service\ExamService.java src\com\exam\ui\ExamPanel.java src\com\exam\ui\LoginPanel.java src\com\exam\ui\MainFrame.java src\com\exam\ui\ProfilePanel.java src\com\exam\ui\RegistrationPanel.java src\com\exam\ui\ResultPanel.java src\com\exam\util\Constants.java src\com\exam\util\SessionManager.java
"C:\Program Files\Java\jdk-25\bin\javaw.exe" -cp out com.exam.Main
```

## Notes

- User data is stored locally in the `data/users.dat` file.
- The app is built using Java Swing and uses a card-based UI flow for login, registration, profile, exam, and result screens.
- This project is designed for desktop-based evaluation and is suitable for Java learning and internship tasks.

