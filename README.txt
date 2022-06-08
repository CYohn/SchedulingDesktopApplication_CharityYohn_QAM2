Title: Scheduling Desktop Application
Purpose: This application allows users to add, modify, and delete customers and appointments. The application also allows
users the ability to generate reports and view schedules filtered by customer, contact, weekly, or monthly.

Author: Charity Yohn
Contact: cyohn8@wgu.edu

IDE Information:
IntelliJ IDEA 2021.2.2 (Community Edition)
Build #IC-212.5284.40, built on September 14, 2021

Java Information:
JavaFX runtime version: 17.0.0.1
Java JDK 17.0.3.1
JavaFX-SDK-17.0.0.1
*Note: My SceneBuilder is using an API of version 18 and a warning does pop-up in the console. However, the elements in
use have not shown any compatibility issues with the JavaFX runtime version of 17.0.0.1.

SQL Driver: mysql-connector-java-8.0.29

Running the Program:
Press the play button in your IDE to start the program. When the program starts you will need to enter a username
and password. for testing purposes, you can use username: test password: test or username: admin password: admin.
Upon successful login, you will be taken to scheduling screen and a pop-up will show indicating if the logged-in user
has any upcoming appointments within the next 15 minutes. Press "OK" to close the pop-up.

You will notice that the scheduling screen has a set of secondary tabs to filter appointment by the upcoming month, week,
or all appointments. To navigate to other pages use the tabs at the top of the screen. Please note that tabbing away
from a screen and going back to the screen by re-selecting the tab will reload the screen and all tables and fields within
the newly selected screen.

When entering fields, text fields have a character limit of 50 characters. When making selections from combo boxes
(drop-down menu boxes) You must click on another field or click on a blank screen area for the entry to register. The
reports page is the most evident of this. After you make a selection click off of the menu box and the tables will load.
Please note that all fields must be completed before saving or modifying an entry for customers or appointments.

Additionally, appointments must be in the future and the end time and date must be after the start time and date. Finally, appointments
cannot overlap with other appointments for the customer. If an existing appointment is found, a pop-up will display advising
of the overlapping appointment and the appointment will not save. Consecutive appointments are allowed. When deleting an
appointment, a pop-up will ask for confirmation. If the user presses cancel the appointment will not delete. If the user
confirms the decision to delete the appointment, only then will the appointment be removed from the database.

To modify a customer or appointment simply select the customer or appointment you wish to modify from the corresponding table
on the modify page. The fields will populate with the current information for the selection. Change any information desired
and click save. The clear button will clear all existing selections and entries, as will reloading the page by tabbing away and back.

All log-in attempts are logged to the file names login_activity.txt.

Additional Reports:
The month and type boxes each produce their own result, and the combination of month and type creates a dual filter. In
addition to the filter by contact feature, I also added a filter by customer feature. Select the customer from the combo box,
click off of the box and the table will populate with all appointments for the customer.

Location of Lambdas:
 You will find a lambda in the schedule controller in the loadCurrentWeek() method. This lambda is part of a stream
which filters appointments by the current number of the week of the year. Finally, you will find another lambda on the
reports controller in the appointmentsCount(). This lambda is also part of a stream used to filter and count the number of
appointments by month, type, and the combination of month and type.

