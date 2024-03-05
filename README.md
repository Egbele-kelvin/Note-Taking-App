# Note-Taking App

This Android application is a simple yet powerful note-taking tool designed using Hexagonal Architecture to ensure clean separation of concerns and Jetpack Compose for a modern, declarative UI. It leverages Room for data persistence, ensuring that your notes are securely stored and retrieved even when the app is closed or the device is compromised.

## Features

### Create, Read and Delete Notes: 
Easily manage your notes with basic CRUD operations.
### Data Security: 
With Room's encryption capabilities, your notes are securely stored on your device.
### Modern UI: 
Built with Jetpack Compose, offering a smooth and responsive user experience.
Clean Architecture: Implements Hexagonal Architecture for maintainable and scalable code.


## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

1. Android Studio Electric Eel or later
2. JDK 11 or later
3. An Android device or emulator running Android API level 26 or higher

### Installation

 #### Clone the repository


```git
git clone https://github.com/Egbele-kelvin/Note-Taking-App

```

## Architecture Overview

The application is structured into three main layers, following the Hexagonal Architecture principles:


### Domain Layer:
 Contains the business logic of the application.

### Data Layer:
 Manages data persistence and Storing data.

### Presentation Layer:
  Handles the UI of the application with Jetpack Compose.



## Technologies Used
- Kotlin - The primary programming language
- Jetpack Compose - Modern UI toolkit for Android
- Room Database - Abstraction layer over SQLite for data persistence
