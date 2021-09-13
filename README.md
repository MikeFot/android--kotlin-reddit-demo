# Reddit Demo

Sample Android Kotlin Recycler View demo fetching data from Reddit Android.

## Architecture

- The app is entirely written in [Kotlin](https://kotlinlang.org/).
- [Koin](https://insert-koin.io/) is used for Dependency Injection.
- Threading is handled by [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html).
- The Activity has its own [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that emits actions via LiveData.
- Remote Data: Network calls are handled by [Retrofit 2](https://github.com/square/retrofit).
- Local Data: There is a local [Room Database](https://developer.android.com/topic/libraries/architecture/room) for persisting fetched posts. There is also an extra table created for handling **Paging2**.
- Data loading and writing is abstracted behind **Repositories**.
- Business logic is handled by reusable **Interactors**.

## Dependencies

* [Kotlin](https://developer.android.com/kotlin/)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)
* [AndroidX](https://developer.android.com/jetpack/androidx/)
* [Paging Library](https://developer.android.com/topic/libraries/architecture/paging)
* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Card Stack View](https://github.com/yuyakaido/CardStackView)
* [Koin](https://insert-koin.io/)
* [Retrofit 2](https://github.com/square/retrofit)
* [Timber](https://github.com/JakeWharton/timber)


