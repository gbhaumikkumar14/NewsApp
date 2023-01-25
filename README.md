# NewsApp
<table>
<tr>
  <td><img src='/Screenshots/Screenshot1.png' width="180" height="320"/></td>
  <td><img src='/Screenshots/Screenshot2.png' width="180" height="320"/></td>
  <td><img src='/Screenshots/Screenshot3.png' width="180" height="320"/></td>
</tr>
  </table>

News App was created using below android components and libraries. Great example to understand MVVM and Dagger HILT DI. Application uses open api for news called newsapi.org

* Kotlin
* Kotlin Coroutine
* Room Database
* ViewModel
* HILT (Dependency injection)
* Retrofit
* GSON
* Glide
* Navigation
* View Binding
* Mock web server
* Truth

Application has mainly 3 screens as displayed above in screenshots
1. Top Headlines
2. Saved News
3. Info Screen

Also application has search news functionality in Top Headlines. Pagination is implemented to get the more news when user reaches at the end of recyclerview. Saved screen has swipe to delete feature.
# Data Management

Application has 2 data sources,

1. Remote Data Source (REST API)
2. Local Data Source (Room Database)
