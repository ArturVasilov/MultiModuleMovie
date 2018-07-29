# MultiModuleMovie

This is sample of multi-module Android project architecture. The main idea is to show how to split code on modules, organize dependencies, interact between features and how to work with dagger 2 in multi-module project.

The main goal here is not a fast build, since multi-module architecture has much more benefits.
For more details see lecture video <here will be link to video, but not very soon sadly>.

# Before you run this project

You'll need to API keys, one for [themoviedb.org](https://developers.themoviedb.org/3/getting-started/introduction) and one for [YouTube](https://developers.google.com/youtube/android/player/register).
Once you have these keys, please open `gradle.properties` file in root directory and replace `movieDb.apiKey` and `youtube.apiKey` respectively (of course it's better to set these props on your CI-server).
