# Assignment 1 - *Flickster (Android)*

**Flickster** is a read-only movie listing app using the Movie Database API.

Submitted by: **RPrasad**

Time spent: **16** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
* [X] Lists should be fully optimized for performance with the ViewHolder pattern.
* [X] Views should be responsive for both landscape/portrait mode.
        - In portrait mode, the poster image, title, and movie overview is shown.
        - In landscape mode, the rotated layout should use the backdrop image instead and show the title and movie overview to the right of it.

The following **optional** features are implemented:

* [X] Stretch: Replace android-async-http network client with the popular OkHttp or Volley networking libraries for all TheMovieDB API calls. 
* [X] Stretch: Display a nice default placeholder graphic for each image during loading (read more about Picasso) (1 point)
* [X] Display a nice default placeholder graphic for each image during loading (read more about Picasso) (1 point)
* [X] Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
* [X] For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and    overview is listed. Use Heterogenous ListViews and use different ViewHolder layout files for popular movies and less popular ones. (2 points)
* [X] Stretch: Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity. (3 points)
* [X] Stretch: Allow video posts to be played in full-screen using the YouTubePlayerView (2 points)
* [X] Stretch: Add a play icon overlay to popular movies to indicate that the movie can be played (1 point).
* [X] Stretch: Apply the popular ButterKnife annotation library to reduce view boilerplate. (1 point)
* [X] Stretch: Add a rounded corners for the images using the Picasso transformations. (1 point)
* [X] Stretch: Replace android-async-http network client with the popular OkHttp networking library for all TheMovieDB API calls. (1 points)


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![Video Walkthrough](flicksterdmo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [2017] [RPRASAD]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
