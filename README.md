### README.md

# Investment Portfolio Tracking App

InvestFolio is a modern Android application designed to empower users in managing their investment portfolios effectively. Leveraging the Model-View-Intent (MVI) architecture, the app provides a seamless and reactive user experience.

## Key Features

- **List Display**: Show a list of investments.
- **Sorting**: Sort investments by value in ascending or descending order.
- **Indicators**: Display up/down arrows based on whether the current value is greater or less than the principal amount.
- **Detail View**: View detailed information for a selected investment.
- **Error Handling**: Manage various JSON response scenarios including empty, malformed, and error responses.

## Tech Stack

- **Kotlin**: Programming language used for Android development.
- **Jetpack Compose**: Modern toolkit for building native Android UI.
- **MVI Architecture**: Model-View-Intent architecture for state management.
- **Hilt**: Dependency Injection framework.
- **Coroutines**: For asynchronous programming.
- **Material Design**: Design principles for UI/UX.
- **Timber**: A logging library for efficient debugging.

## Architecture

The app follows the MVI (Model-View-Intent) architecture to manage state and data flow effectively.
MVI Architecture Diagram

![image](https://github.com/user-attachments/assets/f3dd0e72-60f3-48ee-ad4e-814a80c6c8fe)


### Model

- **InvestmentRepository**: Handles data retrieval from JSON files.
- **InvestmentMapper**: Transforms raw data into usable models.

### View

- **InvestmentApp**: Main composable function setting up navigation and top bar.
- **DataScreen**: Displays the list of investments.
- **DetailScreen**: Shows detailed information for a selected investment.
- **HeaderBar**: Top bar with dynamic title and navigation actions.

### Intent

- **InvestmentViewModel**: Manages user interactions and updates the state.

### Flow
- **User Interaction**: The user interacts with the UI (View), triggering actions.
- **Intent Handling**: These actions are processed by Intent, which updates the ViewModel.
- **State Management**: The ViewModel processes the actions and retrieves or updates data from the Model.
- **Model Updates**: The Model handles business logic and data operations.
- **UI Update**: The ViewModel sends state updates back to the View, reflecting changes in the UI.

## JSON Files

- **`investments.json`**: Contains a list of investments.
- **`investments-empty.json`**: Simulates an empty investment list.
- **`investments-malformed.json`**: Simulates a malformed response.
- **`investments-error.json`**: Simulates an error response.

## Getting Started

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/your-username/investment-portfolio-app.git
    ```

2. **Open in Android Studio**:
    - Open the cloned project in Android Studio.

3. **Build and Run**:
    - Ensure you have a device or emulator set up.
    - Build and run the project.

## Screenshots

Include screenshots of the app to showcase its functionality and design.

[investmentfolio.webm](https://github.com/user-attachments/assets/e6ccde1d-3625-4db1-a1a2-7704993a8625)
| Feature | Screenshot | 
|---|---| 
| Home Screen | <img src="https://github.com/user-attachments/assets/1f660765-7af1-4b26-aae2-75c30296aa30" width="200"> | 
| Details Screen | <img src="https://github.com/user-attachments/assets/a1e81436-0cef-40a9-9cc0-8d8c912a4de9" width="200"> |

---
