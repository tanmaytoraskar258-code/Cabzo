# Cabzo 🚖

[![Platform](https://shields.io)](https://android.com)
[![Language](https://shields.io)](#-tech-stack)
[![License](https://shields.io)](https://opensource.org)

**Cabzo** is a modern, full-scale ride-sharing mobile application built for Android. Modeled after world-class platforms like Uber, Cabzo bridges the gap between riders and drivers through fluid real-time communication, precise location tracking, and an intuitive user interface.

---

## ✨ Features

### 👤 Rider Experience
* 🗺️ **Live Map Tracking:** Watch available drivers navigate the streets nearby in real time.
* 📍 **Smart Destination Search:** Fluid autocomplete search to instantly pinpoint pickup and drop-off points.
* 💰 **Dynamic Fare Estimation:** Immediate price breakdowns calculated instantly based on traffic and route distance.
* 📜 **Trip Journals:** A comprehensive ride history to revisit past trips, fares, and receipts.

### 🚘 Driver Mechanics
* ⚡ **Availability Toggle:** Seamless online/offline switch allowing drivers to control their working hours.
* 📩 **Smart Ride Requests:** Instant interactive popups revealing pickup ranges, travel destinations, and estimated payout values.
* 🗺️ **Turn-by-Turn Routing:** Visual routing that maps out optimal pathways straight to the rider and destination.

---

## 🛠️ Tech Stack & Architecture

Cabzo leverages modern development practices to ensure clean code layout, minimal latency, and robust architectural scaling:

| Layer | Technologies Used |
| :--- | :--- |
| **Frontend UI** | Jetpack Compose / XML Layouts, Material Design 3 |
| **Core Architecture**| MVVM (Model-View-ViewModel), Jetpack Lifecycle components |
| **Mapping & Location**| Google Maps SDK, Google Places Engine, Fused Location Provider |
| **Data & Core State**| Firebase Ecosystem (Auth, Realtime Data Streams, Firestore) |
| **Asynchronous Engine**| Coroutines & LiveData |

---

## 📸 App Preview

| Rider Interface | Driver Dashboard | Active Navigation |
| :---: | :---: | :---: |
| _[Place Rider Screenshot Here]_ | _[Place Driver Screenshot Here]_ | _[Place Map Screenshot Here]_ |

> **💡 Tip:** You can upload screenshots directly into your GitHub issues or a `/screenshots` folder, then link them above to make your repo visually stunning!

---

## 🏁 Getting Started

Get a local instance of the application up and running on your workstation.

### Prerequisites
* Android Studio (Latest stable version)
* Android SDK 34+
* A connected Android device or active Emulator

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com
   ```

2. **Open the Project File**
   * Launch Android Studio.
   * Navigate to **File > Open**, select the cloned `Cabzo` directory, and let the Gradle sync finish.

3. **Deploy & Run**
   * Connect your physical test device via USB Debugging or start your virtual emulator.
   * Click the **Run** button (`Shift + F10`) in the Android Studio toolbar.

---

## 🤝 Contributing

Contributions make the developer community an amazing place to learn, inspire, and create. Any contributions you make to improve the Cabzo ecosystem are **highly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
