# Jellylist
A native Jellyfin client for android written in Jetpack Compose and also has a CLI

# Build from source
clone the repo and open the project in Android Studio or Intellij EAP with latest android plugin.
The IDE should detect Android module and you can run it on yur device.

### Modules
- The module `app` contains Android source code, `app-features` folder will contain android related
modules, like screens and activities.
- Module `cli` contains pure Kotlin code, meaning it can be built and run on any device that has
Java 11 installed. This is where the code is for CLI interface. Just like `app-features` there is
`cli-features` folder containing modules related to CLI.
- `features` module contains login and libraries that can be run both on Android and on CLI variant

# Resources

### Architecture
Architecture is similar to [Nekome](https://github.com/Chesire/Nekome). But there are some modules without Android dependency and
a variant for CLI app.

### Testing
Currently this is in progress.


naming [convention](https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html) for tests 

### Coroutines
https://developer.android.com/kotlin/coroutines/coroutines-best-practices
https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad

### Readme
https://github.com/matiassingers/awesome-readme
### Jellyfin API
https://api.jellyfin.org/#operation/GetMediaFolders
### Koin DI
https://insert-koin.io/docs/reference/introduction
### Icon for the app
<a href="https://iconscout.com/icons/j" target="_blank">J Icon</a>
by <a href="https://iconscout.com/contributors/mcgandhi61">Mohit Gandhi</a>
on <a href="https://iconscout.com">IconScout</a>
