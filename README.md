## About

The app allows you to browse devices and steering them. Also you can see and edit a user
information.

## Architecture

The architecture is based on Google recommended
architecture (`UI layer -> Domain layer(Optional) ->Data layer`).

<img src="assets/arch-google.png" align="center"/>

Modules are separated to 2 main types: `ui` (screens) and `feature` (general and business logic).

ui:

- account - includes screen where you can look at account info (`MyAccountFragment`)
- home - includes screens where you can browse devices and manage them (`HomePageFragment`
  and `DeviceSteeringFragment`)

feature:

- datastore - includes a storing logic
- device - includes logic for device management
- navigation - includes constants for navigation
- ui-code - includes general logic for UI
- user - includes logic for account management

## Used technologies

- [Kotlin](https://kotlinlang.org/docs/home.html) - cross-platform, statically typed,
  general-purpose programming language with type inference.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - components that generalize
  subroutines for non-preemptive multitasking, by allowing execution to be suspended and resumed.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - dependency
  injection library for Android.
- [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel) - architectural
  pattern.
- [Navigation component](https://developer.android.com/guide/navigation) - library for navigation.
- [Compose](https://developer.android.com/jetpack/compose) - modern toolkit for building native UI.
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin) - JSON parser.
- [Timber](https://github.com/JakeWharton/timber) - logger.
- [JUnit4](https://junit.org/junit4/) - unit testing framework.
- [Mockk](https://mockk.io/) - mocking library for Kotlin.
- [Turbine](https://github.com/cashapp/turbine) - library for Kotlin coroutines.