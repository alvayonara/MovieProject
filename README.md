# MovieProject
<img alt="api" src="https://img.shields.io/badge/API-24%2B-green?logo=android"/> <img alt="license" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/>

Simple movie catalogue apps using [TMDB API](https://developers.themoviedb.org) to get movie data.<br/>
There are 3 features on this apps:
1. Discover movies by category (Popular, Upcoming, Top Rated, Now Playing)
2. See detail movie information
3. Save movie to favorite

## Download

Go to this [MovieProject Circle CI](https://circleci.com/gh/alvayonara/MovieProject) to download the APK debug directly.

## Stack and Libraries

* [Coroutines Flow](https://developer.android.com/kotlin/flow/) - emit multiple values sequentially.
* [Jetpack Components](https://developer.android.com/jetpack/)
  - LiveData
  - Lifecycle
  - ViewModel
  - Room
* [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - separates code into layers.
* [Dagger 2](https://github.com/google/dagger/) - dependency injection.
* [Retrofit2](https://github.com/square/retrofit/) - REST APIs.
* [Moshi](https://github.com/square/moshi/) - modern JSON library.
* [Timber](https://github.com/JakeWharton/timber/) - logger.
* [Shimmer](https://github.com/facebook/shimmer-android/) - loading indicator.
* [Material Ripper](https://github.com/balysv/material-ripple/) - ripple effect wrapper.
* [Expandable TextView](https://github.com/Manabu-GT/ExpandableTextView/) - expand/collapse TextView.
* [Mockito](https://github.com/mockito/mockito/) - unit testing.
* [Kotlin Coroutines Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - unit test Coroutines.

## Architecture
<img src="https://github.com/alvayonara/MovieProject/blob/main/readme_files/app-structure.png" width="600"/>

## App Preview
<img src="https://github.com/alvayonara/MovieProject/blob/main/readme_files/app-preview.png" width="750"/>

## License

```
Copyright 2021 Alva Yonara Puramandya

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
