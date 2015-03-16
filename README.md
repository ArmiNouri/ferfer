#Crawl Public Feeds on FriendFeed.com

This application crawls public or accessible feeds on FriendFeed via DFS, starting from your personal profile.
Content is stored in sql-ready relational files with .tsv format. 
Uploaded files and media are also stored into separately indexed folders.
For more information on indexing and relations, you can inspect the application's models.
 

##To use this app:
* rename src/config.properties.example to config.properties
* open src/config.properties
* enter your username on line 3
* browse to http://friendfeed.com/account/api and record your remote key
* enter your remote key on line 5
* enter the full path to the directory where you'd like to store the files on line 9
* build and run org.arminouri.ferfer.FerFer.main


##Configuration parameters
* parameters are specified in "project.home/source/config.properties.example" (make sure to rename this file to config.properties prior to runnign the app)
* username is your username on friendfeed.com
* remotekey can be obtained by visiting http://friendfeed.com/account/api
* HOME specifies where you like the relational files to be downloaded
* maxsize specifies when you'd like the app to stop (currently the app stops once the ff_posts table exceeds 1GBs of data)


##Important Note:
This app is modified to work asynchronously in case of timeouts and unexpected exceptions.
Thus the resulting relational tables might include redundancy.
If you decide to import the files in to an PK-indexed sql database, please be sure to use the "IGNORE" or "REPLACE" flags to avoid key-violation errors. 
All .tsv files are UTF-8 encoded, however this does not eliminate the possibility of encoding errors if you decide to import the files into a sql database.
