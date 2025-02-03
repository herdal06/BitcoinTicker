# 📈 BitcoinTicker

<p align="center">  
BitcoinTicker is a modern cryptocurrency tracking application built with Jetpack Compose, following MVVM clean architecture and utilizing multiple data sources.
</p>

## Features
- Real-time cryptocurrency price tracking
- Search functionality for finding specific cryptocurrencies
- Detailed coin information
- Favorite coins system for quick access
- Offline support with local caching

## App Screenshots

| Auth Screen | Home | Coin Detail |
|:-:|:-:|:-:|
| <img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/auth.jpg" width="250"/> | <img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/home.jpg" width="250"/> | <img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/coindetail.jpg" width="250"/> |
| Favorites | Not Favorites Yet | Search |
| <img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/fav.jpg" width="250"/> | <img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/nofav.jpg" width="250"/> | <img src= "https://github.com/herdal06/BitcoinTicker/blob/master/arts/search.jpg" width="250"/> 

## Architecture
The app follows Clean Architecture principles with MVVM pattern:
<p align="center">
<img src="https://github.com/herdal06/BitcoinTicker/blob/master/arts/mvvm_architecture.png" width="600"/>
</p>

The architecture ensures:
- Separation of concerns
- Testability
- Scalability
- Maintainability

## Data Sources
- **Remote API**: For real-time cryptocurrency data
- **Room Database**: Local caching and offline support
- **Firebase Firestore**: Cloud storage for user preferences and favorites

## Tech Stack
* ✅ **UI**
  - Jetpack Compose

* ✅ **Architecture**
  - MVVM Clean Architecture
  - Repository Pattern
  - Use Cases

* ✅ **Data Management**
  - Kotlin Flow
  - StateFlow
  - Room Database
  - Firebase Firestore

* ✅ **Dependency Injection**
  - Hilt

* ✅ **Network**
  - Retrofit
  - OkHttp
  - Kotlin Serialization

* ✅ **Testing**
  - Unit Tests
  - JUnit
  - Mockk

* ✅ **Others**
  - Coroutines
  - Coil for image loading
  - Navigation Component

## Testing
The project includes comprehensive unit tests covering:
- Repositories
- Data Sources
