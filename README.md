# Knowledge Siege
Knowledge Siege is a strategy and survival game project developed for the Koç University COMP132: Advanced Programming course, utilizing the Java Swing library and focusing on core Object-Oriented Programming (OOP) principles such as inheritance, abstraction, polymorphism, and interfaces.

## Gameplay & Features
- **Information vs. Questions:** Players collect "Information" (light bulb icons) to gain points while avoiding "Questions" (question mark icons) that reduce health (HP).
- **User Management:** Features a comprehensive system for registration, secure login with password visibility toggles, and persistent user data.
- **Scoreboard:** A high-score table that sorts game sessions in descending order of scores, using alphabetical sorting as a tie-breaker.


<img width="800" height="660" alt="Ekran Resmi 2026-02-17 12 19 34" src="https://github.com/user-attachments/screenshots/42cb374f-f7d9-473d-8f86-dceb3f2d1101" />

## Technical Architecture (OOP)
The project is organized into 7 main packages to ensure modularity and scalability : `characters`, `data`, `user`, `shotbox`, `game`, `screens`
- **Polimorfizm:** Different enemy types, derived from the abstract `KnowledgeKeeper` class, have different firing and movement logic.
- **Veri Yönetimi:** User data and game logs are stored under the `data/` folder.

## Documentation
For a deep dive into the software design, class relations, and implementation details, please refer to the official project report:

👉 **[View Project Report (PDF)](docs/mkoksal23.pdf)**


## Academic Integrity
This project was developed by Mert Bayram Köksal as part of the COMP132 course at Koç University . It strictly adheres to the university's academic integrity guidelines. The "Pledge of Honor" text is included at the beginning of the source code.

Developer: Mert Bayram Köksal

Term: Spring 2025
