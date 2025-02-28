# LocalPasswords
<img src="HnVideoEditor_2023_07_31_192839932.gif" width=20%>

## Description:
In our time and age, sensitive personal data like passwords is leaked to the internet oh so often. Cloud password managers are no exception to this rule –just recently, 22th of December 2022, LastPass’s database was leaked, and this happens time and time again. Of course, no one is interested in security of my passwords more than I am, that's why I've decided to build this application.

LocalPasswords does exactly what it says – stores passwords locally on an Android device in an SQLite database. After that, the user can view all added accounts. To view an account’s password, one must tap a list item with this account written on it. There is a possibility to add accounts to database, edit database entries or completely delete them.
This application is built using Java, XML markup language and SQLite database.
There are four activities (working screens) and two java classes that are not an activity in this application.

### MainActivity
MainActivity is used as a blank activity to prompt the user to scan a finger or draw a graphic key.
If neither is done, the activity stays hidden and nothing else happens. If user does what is needed and everything is OK, activity_main layout becomes visible, revealing a button, that can be used later to get to the second activity. In addition, on login this transition happens automatically. I could have implemented a biometric prompt on the second screen 'ResultActivity' and just hide its layout, but I am not sure if that would be safe enough.

### ResultActivity
This activity is where all the action happens. Upon logging in, user is provided with a list of entries, every one of which displays an account name and a ‘filter’ – a flag, by which user can identify, what type of an account this id (i.e. email, site, social network etc.). As we can see, passwords are not shown in list for additional security.

When the user taps on an account which password is needed, a popup message is called using AlertDialog.Builder object. This message shows the password and brings up a ‘Copy’ button to copy that password to clipboard for pasting it where it is needed later.

If the user uses not a short tap, but a long one, another activity is called – ‘ModifyId’, which is used to modify or delete that long-tapped account. We’ll get back to it in a bit.

On top of the list is an ‘Add’ button that is used to add entries to database. When the user presses this button, another activity – ‘AddId’ – is called.

### ModifyId
We get this activity by long pressing the list entity with an account we want to modify or delete. Layout named ‘activity_id_modify’ contains three text fields with that account's name, its password and filter inside them, and also buttons ‘Update’ and ‘Delete’. To update an account, we change the text fields and press the ‘Update’ button. Pressing ‘Delete’ button simply deletes database entry with this account. Any of those two actions upon completion returns us to ‘ResultActivity’.

### AddId
We get this activity by pressing an ‘Add’ button at the top of the list. Its ‘activity_id_add’ layout contains the same three fields (account, password, filter) as the ‘activity_id_modify’ layout, but this time there is only one button – ‘Add’. Filling in those forms and pressing the button saves the forms’ values to corresponding columns in the database table.

Speaking of the table, the remaining two java classes relate to it. They are really useful for operating with an SQLite database from several activities. Let’s look at them a little bit closer.

### DatabaseManager
This class contains methods to open, close, update or delete a database or to crawl it with a Cursor object in order to output a list in a ‘ResultActivity’.
It allows us to address to our table from different activities. Without this class, we would have to define all those methods in every activity.

### DataBaseHelper
This class extends SQLiteOpenHelper class and contains all our SQLite statements in it, so that there is no need to retype them in every activity.

## Conclusion:

And that’s pretty much all there is. A couple of thoughts on this project:
- This project was quite challenging for me, because I have not only had to get acquainted with Java and XML, but also because SQLite’s implementation in Android is quite different syntactically than what we have learned on CS50.
For learning Java and XML I have used standard android developer guide and references:
https://developer.android.com/guide,
https://developer.android.com/reference/
But I have to admit, they often seemed difficult for me to understand as one less-comfortable with programming.
So, StartAndroid course on Java Andriond development was really helpful
https://startandroid.ru/ru/uroki/vse-uroki-spiskom.html
Nevertheless, it has its own flaws. It was written mainly in 2011-2012 and lots of things have changed since then in Andoid. Many methods became deprecated, some new additions were made etc.
Combining these two courses helped in building the majority of an application.

- However, using only those two would have left me with defining all SQLite statements in every other activity, which is highly uncomfortable, as standard lessons usually explain SQLite support only in context of only one activity.
Anupam’s tutorial on using SQLite in Andoid helped me a lot with that:
https://www.digitalocean.com/community/tutorials/android-sqlite-database-example-tutorial.
Also, using SQLite cursor to return a list is explained pretty well there. But it has its flaws too, as it misses one of the example files. And I’ve had to make some changes to code to make it more suitable for my application: different table structure and different methods for interacting with list items.

-  For adding a fingerprint scanner I’ve consulted with Tech Projects’ youtube video Biometric Fingerprint Authentication in android studio:
https://youtu.be/Jt-F7OSb_LU.
Since it’s also a pretty new way of doing it by using BiometricManager, there are not that many tutorials. And although that video is only one year old, it already has two methods that are considered deprecated by now! That’s insane…

- And lastly, talking about future improvements of this app, I plan to add these features in the nearest future:
1. Encrypting the database. Right now this application prevents only average users from getting to my sensitive data, but advanced users can get database from device memory itself and read it without any difficulties. Database encryption will solve this problem.
2. Account filters need to be improved so that when we select a ‘mail’ filter, for example, list will show us only accounts that are tagged as ‘mail’.
3. Ability to choose which of the saved fingerprints can unlock this app. Often children have their fingerprints saved to use parent’s phone for gaming or something else, but we don’t want them to get access to that sensitive data.

Thanks for reading 'til the end :)
