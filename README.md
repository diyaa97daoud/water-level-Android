# Water Level Monitoring - Android Application

Android mobile application for monitoring and controlling water level systems in real-time.

## 🚀 Features

- **Real-time Monitoring**: View water level readings and pump status in real-time
- **Device Management**: Monitor multiple water level monitoring devices
- **Authentication**: Secure login with JWT token-based authentication
- **Threshold Management**: View and track water level thresholds
- **User-friendly UI**: Modern Material Design 3 interface built with Jetpack Compose

## 📋 Prerequisites

- Android Studio (latest version recommended)
- JDK 11 or higher
- Android SDK (API 24 or higher)
- Backend server running

## 📁 Project Structure

```
WaterLevel Android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/waterlevelmonitoring/
│   │   │   │   ├── data/
│   │   │   │   │   ├── remote/         # API service and Retrofit client
│   │   │   │   │   ├── repository/     # Data repositories
│   │   │   │   │   └── AuthTokenHolder.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/     # Reusable UI components
│   │   │   │   │   ├── devices/        # Device list screen
│   │   │   │   │   ├── details/        # Device details screen
│   │   │   │   │   ├── login/          # Login screen
│   │   │   │   │   └── theme/          # App theming
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/                    # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                # Instrumented tests
│   │   └── test/                       # Unit tests
│   ├── build.gradle.kts                # App-level Gradle config
│   └── proguard-rules.pro
├── gradle/                             # Gradle wrapper files
├── build.gradle.kts                    # Project-level Gradle config
├── settings.gradle.kts
└── README.md

```

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with:

- **Jetpack Compose**: Modern declarative UI framework
- **Retrofit**: HTTP client for API communication
- **Kotlin Coroutines**: Asynchronous programming
- **ViewModel**: UI state management
- **Repository Pattern**: Data layer abstraction
- **Material Design 3**: Modern UI components

## 🔧 Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/diyaa97daoud/water-level-Android
cd "WaterLevel Android"
```

### 2. Configure Backend URL

Open `app/src/main/java/com/example/waterlevelmonitoring/data/remote/RetrofitClient.kt` and update the base URL:

```kotlin
private const val BASE_URL = "http://YOUR_SERVER_IP:8080/api/"
```

**Note**:

- For local development with emulator: Use `http://10.0.2.2:8080/api/`
- For physical device: Use your computer's IP address (e.g., `http://192.168.1.100:8080/api/`)

### 3. Sync Gradle Dependencies

Open the project in Android Studio and wait for Gradle sync to complete automatically, or manually trigger it:

- Click **File** → **Sync Project with Gradle Files**

### 4. Build and Run

1. Connect an Android device via USB (with USB debugging enabled) or start an Android emulator
2. Click the **Run** button (▶️) in Android Studio
3. Select your target device
4. Wait for the app to build and install

## 📱 Using the App

### Login

1. Open the app
2. Enter your credentials:
   - Username: default `admin`
   - Password: default `admin123` (or your configured credentials)
3. Tap **Login**

### Monitor Devices

1. After login, you'll see a list of registered devices
2. Tap on any device to view detailed information:
   - Current water level
   - Pump status (Running/Stopped)
   - Historical readings chart
   - Threshold settings

## 🔑 API Configuration

The app connects to the backend REST API with the following endpoints:

- `POST /auth/login` - User authentication
- `GET /devices` - Fetch all devices
- `GET /devices/{id}/thresholds` - Get device thresholds
- `GET /water-level/device/{id}` - Get water level readings
- `GET /pump/{id}` - Get pump status
- `POST /pump/{id}/start` - Start pump
- `POST /pump/{id}/stop` - Stop pump

## 📦 Dependencies

Key libraries used:

- **Jetpack Compose**: Modern UI toolkit
- **Material Design 3**: UI components
- **Retrofit 2**: HTTP client
- **OkHttp**: HTTP client implementation
- **Gson**: JSON serialization
- **Kotlin Coroutines**: Asynchronous operations
- **AndroidX Lifecycle**: ViewModel and LiveData
- **Navigation Compose**: Screen navigation

## 📞 Related Projects

- [Backend Server](https://github.com/aliyehiawi/water-level-monitoring-backend) - Java Spring Boot REST API
- [Web Dashboard](https://github.com/aliyehiawi/smart-garden-frontend) - Vue.js web interface
- [IoT Firmware](https://github.com/diyaa97daoud/water-level-Embedded) - Embedded system firmware
