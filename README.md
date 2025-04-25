# 📱 Mobile Weight Tracker

A modern, user-centered mobile app developed in Android Studio using Java and XML that helps users track their daily weight, set goals, and stay motivated.

---

## 🧭 App Goals & Purpose

The goal of this project was to design and develop a weight-tracking mobile app that meets real user needs. The app allows users to:
- Register and securely log in to their own account
- Record daily weight entries with associated dates
- Set a target goal weight
- View their progress in a clean, scrollable grid
- (Optionally) receive SMS alerts when their goal weight is reached

This app was built with simplicity and clarity in mind — ideal for users looking for a lightweight and private way to manage their fitness progress.

---

## 🖥️ Key Features & Screens

- **Login/Register Screen:** Allows secure account creation and login with persistent storage via SQLite.
- **SMS Permission Screen:** Gives users the option to enable or decline SMS notifications. Their choice is remembered.
- **Main Grid Screen:** Displays all recorded weights in a structured table, ordered by date (newest to oldest). Includes:
  - Goal weight display
  - “Record Latest Weight” button (popup dialog)
  - “Set Goal Weight” button (popup dialog)
  - Row-based delete buttons

### 👥 User-Centered UI Design

The UI was designed to be intuitive, minimal, and responsive:
- Simple vertical flows to reduce complexity
- Clean buttons and spacing for easy interaction
- Popups and toast messages to provide feedback without clutter
- User choices (like SMS permission) are respected long-term

---

## 💻 Development Process

The app was developed using:
- Java + XML in Android Studio
- Modular screen-based activity structure
- SQLite database for login info, weight entries, and goal storage
- `SharedPreferences` for lightweight settings (e.g. SMS opt-in)

I broke the app into small, testable components — login logic, DB handling, UI screens — and built incrementally. This made debugging easier and ensured each part worked independently before integration.

---

## 🧪 Testing Strategy

Testing was done on the Android Emulator and focused on:
- Login/registration validation
- SQLite insert/delete/read correctness
- SMS permission handling
- UI behavior across permission outcomes

This process revealed the importance of defensive coding (e.g., empty input checks, cursor handling) and helped verify app flow worked across all expected states.

---

## 🚧 Overcoming Challenges

A key challenge was handling SMS permission flow in a user-friendly way. I had to:
- Implement logic to remember the user’s choice using `SharedPreferences`
- Allow the app to continue functioning even if permissions were denied
- Skip the SMS screen entirely on future logins when appropriate

This added nuance to the flow and kept the experience smooth.

---

## 🏆 Project Highlights

One of the strongest aspects of this app is the **integration of user-driven design with technical flow control**:
- A fully functioning weight tracking system with real-time updates
- Persistent goal-setting and clean UI feedback
- Smart permission management and conditional navigation

This project showcases both my development knowledge and my understanding of practical UI/UX principles.

---

## 🚀 Built With
- Java
- Android Studio
- SQLite
- SharedPreferences
- XML Layouts

---

Feel free to fork, build, and expand it! 🎯
