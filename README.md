# Earthquake Notification System
This is a simple project that implements an earthquake notification system using a doubly-linked-list array-based queue. 

There are two object classes created for this program, one created for the watchers, it contains their name and coordinates, and one created for the earthquake, containing its info like place magnitued etc.

The program starts by asking the user to input 2 files, one containing the watchers and one containing the earthquakes, the program reads both files and creates objects for the watchers and earthquakes, and adds them to their respective queues, then the program starts processing both files simultaneously. 

At the beginning of processing a time variable is created and set to 0, each event or command in the watcher and earthquake files has a time variable at which they occured, if the current time matches that time the commands and events in the files are processed.

The commands processed in the watcher files are add to add a watcher, delete to delete a watcher and query-largest to show info about the largest earthquake that happened in the last 6 hours.

The earthquake file contains info about the earthquakes in a specific format, containing info about the earthquakes time, id, place and magnitude. Each time an earthquake is processed, the distance between the earthquake and each watcher is calculated then a list of the closest watchers to the earthquake is printed.

Lastly, if 6 hours have passed since the earthquake, the earthquake gets removed from the list.

Sample watcher file:
```
0 add -105.7 -24.3 Tom
1 add 21.2 -38.6 Jane
4 add -11.0 63.1 Taylor
5 add -79.2 37.3 John
6 add -125.1 -38.5 Henry
8 delete Taylor
10 query-largest
```
Sample earthquake file:
```
<earthquake>
	<id> 001 </id>
	<time> 6 </time>
	<place> 4km East of San Francisco, CA </place>
	<coordinates> -115.5808, 33.0187, 9.5 </coordinates>
	<magnitude> 3.971428571428571 </magnitude>
</earthquake>
<earthquake>
	<id> 002 </id>
	<time> 15 </time>
	<place> 21km SE of Mammoth Lakes, California </place>
	<coordinates> -118.8353, 37.493, 1.9 </coordinates>
	<magnitude> 3.457142857142857 </magnitude>
</earthquake>

```



