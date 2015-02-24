EARTHQUAKEOCCURENCE APPLICATION

Development Environment
This was developed using android studio. It was built with Target SDK as API 21: Android 5.0(Lollipop) and minimum SDK required is API 14: Android 4.0(Icecream Sandwich).
To build this app, copy the entire ‘EarthquakeOccurence’ folder to  your workspace and update ‘sdk.dir’ at ‘EarthquakeOccurence \local.properties’  to point to the appropriate SDK directory.


Project Description
The application’s main UI has 2 dropdown menus for year & month selection. Once the button is clicked, a MapsActivity opens with markers showing earthquake instances. Title gives the magnitude of earthquake. Only earthquakes with magnitude >= 6.0 are shown.
The project uses an Async Task to fetch the data from RESTful api. ‘ JSONResponseHandler’ handles the httpresponse and parses it into an ‘EarthquakeRecord’ ArrayList. ‘onPostExecute’ call of the Async task parcels the ‘EarthquakeRecord’ ArrayList and starts MapsActivity with the parcel passed on as ‘Extra’.

Unit Test
Parsing the httpresponse and filling up the EarthquakeRecord list is unit tested using android provided Junit test framework.  The output of the JSONResponseHandler is compared with a prefilled EarthquakeRecord list. The Java file is available at EarthquakeOccurrence\app\src\androidTest\java\com\anz\meera\earthquakeoccurrence\ ApplicationTest.java

Functional Tests
Following functional tests were done on the Application :
•	Select year & month and click button to see the map opening with markers. Verify the titles.
•	Selecting different years and months on the spinner.
•	Clicking the button without selecting any value. Using the default value for year & month.
•	Select values for year & month. Make the app to go to background. Bring it back to foreground to see the selected values are retained.
•	Click the button without wifi or data connection and verify that app does not crash.
•	Exit the app and bring it back.
•	Perform the app functions multiple times.
•	Try to click the button continuously.

