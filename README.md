# Knowledge Siege
Knowledge Siege is a strategy and survival game project developed for the Koç University COMP132: Advanced Programming course, utilizing the Java Swing library and focusing on core Object-Oriented Programming (OOP) principles such as inheritance, abstraction, polymorphism, and interfaces.

## Tools Used

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,eclipse,git,github" />
</p>


## Gameplay & Features
- **Information vs. Questions:** Players collect "Information" (light bulb icons) to gain points while avoiding "Questions" (question mark icons) that reduce health (HP).
- **User Management:** Features a comprehensive system for registration, secure login with password visibility toggles, and persistent user data.
- **Scoreboard:** A high-score table that sorts game sessions in descending order of scores, using alphabetical sorting as a tie-breaker.

<p align="center">
  <img src="https://github.com/user-attachments/assets/9fcd01da-4eed-4d80-8211-df3455b92b17"
       alt="Knowledge Siege Gameplay Screenshot"
       width="600"/>
</p>

<p align="center">
  <em>Knowledge Siege: Gameplay.</em>
</p>


## Technical Architecture (OOP)
The project is organized into 7 main packages to ensure modularity and scalability : `characters`, `data`, `user`, `shotbox`, `game`, `screens`
- **Polimorfizm:** Different enemy types, derived from the abstract `KnowledgeKeeper` class, have different firing and movement logic.
- **Veri Yönetimi:** User data and game logs are stored under the `data/` folder.

## Documentation
For a deep dive into the software design, class relations, and implementation details, please refer to the official project report:

👉 **[View Project Report (PDF)](docs/mkoksal23.pdf)**
##  How to Run

To run this Java Swing game:

### Requirements
- Java JDK 17 or later
- An IDE

### Steps
1. Clone the repo:
```bash
git clone https://github.com/mertbayramkoksal/KU-COMP132-KnowledgeSiegeGameProject.git
```
2. Open the project in an IDE
3. Run the main class from src

## Academic Integrity
This project was developed by **Mert Bayram Köksal** as part of the COMP132 course at Koç University . It strictly adheres to the university's academic integrity guidelines. The **"Pledge of Honor"** text is included at the beginning of the source code.

**Developer:** Mert Bayram Köksal

**Term:** Spring 2025
