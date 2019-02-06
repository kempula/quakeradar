# QuakeRadar
A little assigment:

Build an Android App that utilizes Earthquake data from the US Government:

http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson

Documentation for the API is available on the USGS website.

The data is refreshed in a regular interval. What you do with that data is entirely up to you.

Surprise us with your creativity but keep in mind that you need not create a full blown service. Pick a few interesting features and focus on the quality of the solution. You shouldn't spend more than a few hours or evenings on this.

Imagine you would need to continue developing and maintaining that application for a while and consider that other people might need to work with your code (including us as reviewers).

Choice of technologies, architecture of your solution etc. are entirely up to you.

Solution:
So, I chose to go with two features: to show the earthquakes on Google Map and in a list with place, timestamp and magnitude. The list also has two sorting options: by magnitude (high to low) and by date and time.

Third party libraries I used: Retrofit, RxJava and Gson (and Google Maps SDK).

<img src="/screenshots/ss1.png" />

<img src="/screenshots/ss2.png" />

<img src="/screenshots/ss3.png" />
