Elections14
===========

Elections14 is a simple Android app to help keep track of the Indian Lok Sabha election results as they come in on May 16, 2014. The data is scraped from the Election Commission's website (http://eciresults.ap.nic.in/) and displayed to the user. The user can view the results within the app, on a homescreen widget and a lockscreen widget.

The app will go live on the morning of May 16, once the Election Commission starts displaying the results. It will be freely available on Google Play and the link will be shared here. Until then, you are free to try the app, with the results currently slightly randomized so as to give you a slight experience of what it would be like. The randomization will be removed in time for the public launch.

Libraries
============
HTMLCleaner has been used for the scraping. I will be separately releasing, which is part of the "JavaScraping" project you would see in the project properties of the app. In a future commit, I will be removing the library and simply adding the jars to the project.

The app also uses a "HaptikLibrary". Within the app, there is an option for users to ask further queries from experts at Haptik. The HaptikLibrary provides the necessary deeplinking support with the Haptik app (https://play.google.com/store/apps/details?id=co.haptik). The library would not be available for a while to general users, so I'd recommend removing it for your own consumption.

Contact
=============
My contact information is available at http://raveesh.co. If you would like to get in touch with me on Twitter, I'm @raveeshbhalla
