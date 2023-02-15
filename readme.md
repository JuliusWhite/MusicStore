# :musical_note: Music store manager with Java and MySQL, following the MVC. :musical_note:

This is a CLI application to manage a music store that sells ambums and posters.

Before start running the application for the first time, the database known as musicstore must exist. You can run the script located in: `Script SQL/MusicStore.sql`.

Once the database exists, the program lets you:
- Show all the articles, in a short and in a long way.
- Add an article to the database.
- Modify some data from an existing article.
- Delete an article from the database.
- Resume of the tickets form the database.
- Show the most sold 5 articles.

The database have two types of articles: albums and posters. In order to addapt this to Java, I use inheritance, gfrom a father class `Article.java`.

When the program chatches a SQL exception, it writes it down in a XML file, named `error.xml`, wich will be located inn the root folder. Each item in the xml file writes the message of the exception, the exception number and the 
