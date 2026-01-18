# MVVM News App

A modern Android application built with Kotlin, following the Model-View-ViewModel (MVVM) architecture. This project demonstrates best practices in Android development, including manual dependency injection, Repository pattern, and offline caching.

## 🏗 Architecture

The app uses the **MVVM (Model-View-ViewModel)** architecture:

- **Model**: Data layer handling data from the Remote API (Retrofit) and Local Database (Room).
- **View**: UI layer (Fragments/Activity) displaying data and capturing user events.
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way and acts as a bridge between the Repository and the View.

## 🛠 Tech Stack & Libraries

- **Kotlin**: 100% Kotlin-based.
- **Coroutines**: For asynchronous programming.
- **Retrofit**: For network requests.
- **Room**: For local database persistence (offline support).
- **Navigation Component**: For single-activity navigation between Fragments.
- **LiveData**: For reactive data updates.
- **ViewBinding**: For safe interaction with views.
- **Glide**: For fast image loading.
- **Safe Args**: For passing data between Fragments.

## ✨ Features

- **Breaking News**: View top headlines which are paginated.
- **Search**: Search for specific news articles.
- **Save Article**: Save favorite articles locally to view offline.
- **Article View**: Read full articles functionality.

## 🚀 Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/MVVMNewsApp.git
   ```
2. Open the project in **Android Studio**.
3. **API Key Setup**:
   - This app uses [NewsAPI.org](https://newsapi.org/).
   - Sign up and get your free API Key.
   - Open `Constants.kt` and replace the placeholder with your key:
     ```kotlin
     const val API_KEY = "YOUR_API_KEY_HERE"
     ```
4. Build and Run the app on an Emulator or Physical Device.

## 📂 Project Structure

```
com.gayan.mvvmnewsapp
├── adapters        # RecyclerView Adapters
├── api             # Retrofit Interfaces & Instances
├── db              # Room Database, DAO, & Entities
├── models          # Data Classes
├── repository      # Single Source of Truth for Data
├── ui              # Activities, Fragments, & ViewModels
└── util            # Constants & Resource Wrappers
```

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📄 License

[MIT](https://choosealicense.com/licenses/mit/)
