Hey there 👋🏼👋🏼👋🏼

This project contains an implementation of the Componentization idea as shown by the UI Engineering team at Netflix.

Resources: [blog](https://netflixtechblog.com/making-our-android-studio-apps-reactive-with-ui-components-redux-5e37aac3b244), [repo](https://github.com/julianomoraes/componentizationArch), [talk](https://www.droidcon.com/media-detail?video=362740979)

## Features
* Clean Architecture with MVI (Uni-directional data flow)
* View components
* Kotlin Coroutines with Flow
* Dagger Hilt
* Kotlin Gradle DSL

## Prerequisite
To build this project, you require atleast:
- Android Studio Arctic fox canary 8
- Gradle 8.7

After cloning the project, do a rebuild or make project to setup all dependencies (On the Android studio top bar, select Build, from the drop down menu, click on `Make Project` or `Rebuild Project`)


## Libraries

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Presenter for persisting view state across config changes
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.  
- [Moshi](https://github.com/square/moshi) - JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters
- [Media3](https://developer.android.com/media/media3) - High-level capabilities for a media player, such as the ability to play, pause, and seek.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the star wars api with the retrofit client.
- [Robolectric](http://robolectric.org/) - Unit test on android framework.
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [Dagger Hilt](https://dagger.dev/hilt) - handles dependency injection

# System Design Document for EdTech App

## Summary of Proposed Solution
The proposed EdTech app is designed to provide learners with an accessible, engaging, and seamless experience for consuming educational video content. The app ensures stability and scalability by utilizing modern Android development best practices, including Jetpack Compose for UI, Room for offline data storage, and ExoPlayer for video playback. The caching mechanism optimizes content delivery by preloading adjacent videos and supports offline playback. The app also enables learners to bookmark and annotate specific sections of videos, enhancing interactivity. Daily login rewards incentivize user engagement, and a robust backend integration ensures reliable data handling.

---

## High-Level Design Documents

### 1. Main Flows for User Journeys
#### 1.1 Browse and Stream Video Lessons
- Users browse a library of video lessons.
- Videos are streamed online using ExoPlayer.
- Caching occurs for the previous, current, and next videos in sequence.
- Cached videos are played offline if available.

#### 1.2 Video Playback with Notes and Bookmarks
- Users can play videos and pause to create notes linked to specific timestamps.
- Notes are saved locally using Room and synced to the backend when online.

#### 1.3 Daily Login Rewards
- Upon completing the first video of the day, users are rewarded with a badge.
- Badge data is stored locally and synced with the backend for consistency.

#### 1.4 Offline Mode
- Cached videos and notes are available offline.
- Users can resume the last unfinished video directly from the home screen.

---

### 2. Interactions with Backend
#### 2.1 Backend Endpoints
- `GET /lessons`: Fetch the list of available lessons.
- `GET /lessons/{id}/video`: Stream video content.
- `POST /notes`: Save user notes.
- `GET /notes/{lessonId}`: Fetch notes for a specific lesson.
- `POST /rewards`: Record daily badge rewards.

#### 2.2 Data Flow
- On app launch, the app fetches lesson metadata and syncs cached notes and rewards with the backend.
- During video playback, the app checks for cached content before initiating a network request.
- Notes and rewards are stored locally first and then synced asynchronously.

---

### 3. Design Patterns, Coding Principles, and Standards
#### 3.1 Design Patterns
- **MVVM (Model-View-ViewModel)**: Ensures separation of concerns, making the app easier to maintain and scale.
- **Repository Pattern**: Centralizes data management and abstracts data sources (local/remote).
- **Observer Pattern**: Used for LiveData to update UI reactively.

#### 3.2 Coding Principles
- **SOLID Principles**: Promote clean, scalable, and maintainable code.
- **DRY (Don’t Repeat Yourself)**: Avoid redundant code by reusing components and utilities.
- **KISS (Keep It Simple, Stupid)**: Simplify logic and avoid overengineering.

#### 3.3 Standards
- Follow Kotlin coding conventions.
- Use Jetpack libraries for modern Android development.
- Write unit tests for ViewModel and repository layers.

---

## UML Diagrams

### Class Diagram
#### Key Components:
- **ChapterRepository**: Handles data retrieval (local/remote).
- **LessonViewModel**: Manages UI state for lessons.
- **ExoPlayerWrapper**: Encapsulates video playback logic.
- **NotesManager**: Manages notes linked to video timestamps.
- **RewardManager**: Handles badge rewards.

**Diagram Structure:**
com.uticodes.mivaandroidtest
├── data
│   ├── models
│   ├── remote
│   │   ├── repository
│   │   │   ├── chapter
│   │   ├── ApiService
│   ├── 
├── usecases
│   ├── GetChapterUseCase
│   │── GetSubjectsUseCase
│   │── ResumeLearningUseCase
│   ├── BookmarkUseCase
├── di
├── ui.theme
├── utils
├── view
│   ├── activity
│   │   ├── MainActivity
│   │   
├   ├──homeScreen
├   ├──player
├   ├──subject
├   ├──viewmodel
├── MainApp

---

### Sequence Diagram: Video Playback Flow
1. **User selects a video.**
2. `HomeViewModel` checks cache via `ChapterRepository`.
3. If cached, `ExoPlayerWrapper` loads the video locally.
4. If not cached, `ExoPlayerWrapper` streams the video and initiates caching for adjacent videos.
5. Notes and playback progress are saved locally during playback.
6. On video completion, `DownloadManager` triggers a badge reward.

**Flow Structure:**
- **User → LessonViewModel**: Play Video
- **SubjectViewModel → ChapterRepository**: Check Cache
- **LessonRepository → ExoPlayerWrapper**: Load Video
- **ExoPlayerWrapper → DownloadManager**: Reward Badge

---

## Prototype Overview

### Screens and Features
#### Home Screen
- Browse lessons.
- Resume last unfinished video.

#### Video Player Screen
- Stream/cached video playback.
- Add bookmarks and notes.
- View notes overlay.

#### Daily Rewards Screen
- Display badge collection status.

### APK and Repository
- **APK Download**: [Link to APK](https://drive.google.com/drive/folders/1vW-cQ8w8dM3lj4gNDY4nxtoDyiMp1cgX?usp=sharing)
- **GitHub Repository**: [Link to Repository](https://github.com/Uticodes/Miva-Android-Test)

---

## Additional Documentation
- **Caching Strategy**: Videos are cached using WorkManager to schedule downloads during idle times and WiFi-only conditions.
- **Offline Sync**: Implemented with periodic syncing using Room and WorkManager.
- **Testing**: Includes unit tests for business logic and UI tests for critical user flows.
