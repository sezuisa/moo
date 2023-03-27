
# moo - the Mood Tracker App

Moo was developed by computer science students Sandro Bühler and Sarah Hägele during their 5th semester. The app allows users to log their mood daily and add a little highlight of the day as well as a brief note. Users can also view their mood history and see a weekly mood statistics report.

## Features

moo provides the following features:

- Mood logging: Users can select and save their current mood.
- Adding a highlight: Users can add and save a highlight of their day (restricted to 30 characters - a highlight is supposed to be just one or two very short buzzwords for the day!)
- Adding a note: Users can add and save a brief note about their day to describe how things went. You can add a lot more text here. In the home view, longer text is scrollable inside the text view.
- Mood history: Users can view the maps of the last days to track their mood changes over time. In the history view, longer text that doesn't fit into a moo card is marked by three dots at the end of the text block - you can click/tap on the text to get a small popup window containing the full text.
- Weekly statistics: Users can view a statistical report of their mood over the past week.
- Themes: Users can choose between three moo themes: Brown (default), Black&White and Milka. All three support light & dark mode.

## Technology

Moo was developed using the Kotlin programming language in Android Studio. The backend uses Realm in conjunction with MongoDB to manage user authentication and maps.

