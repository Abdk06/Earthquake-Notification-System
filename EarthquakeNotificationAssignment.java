/* This is the main file and driver of the assignment, the program asks the user 
   for 2 input files for watchers and earthquakes, then it reads the files, adds
   the actions of watchers in the Watcher Queue and the earthquakes in the Earthquake Queue,
   then proccesses them in the order of the queue.
   This program uses the DoublyLinkedList data structure and the Queue data structure using the
   code uploaded on Piazza, I also added 4 custom methods to the DoublyLinkedList class.
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EarthquakeNotificationAssignment {
    // The main function, everything happens here
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);// Scanner to take name of file from user
    System.out.println("Enter the Watchers file name: ");
    String name1 = input.nextLine();// Wait for user to input name for first file
    File watcherFile = new File(name1);
    ArrayQueue<String> watcherQueue = readWatchers(watcherFile);// Load and read the watcher file and put each action in the queue
    System.out.println("Enter the Earthquakes file name: ");
    String name2 = input.nextLine();
    File earthquakeFile = new File(name2); // Wait for user to input name for second file
    ArrayQueue<Earthquake> earthquakeQueue = readEarthquakes(earthquakeFile); // Load and read the earthquake file and put each earthquake in the queue
    DoublyLinkedList<Watcher> watcherlist = new DoublyLinkedList<>(); // Create list for watchers
    DoublyLinkedList<Earthquake> earthquakelist = new DoublyLinkedList<>(); // Create list for earthquakes
    int time = 0; // Create time for simulation
    while (!watcherQueue.isEmpty() || !earthquakeQueue.isEmpty()) { // Keep the simulation running until both the watcher queue and the earthquake queue are empty
        if (!earthquakelist.isEmpty()) {
            earthquakelist.removeOld(time); // Remove all earthquakes that have been added over 6 hours ago from the list
        }
        while (!watcherQueue.isEmpty()) { // A while loop to ensure all commands are processed
            String[] command = watcherQueue.first().split(" "); // Tokenizing the line
            if (Integer.parseInt(command[0]) == time) { // command[0] is the time of the input, the command is proccessed if the time of the action equals the current time of the simulation
                switch(command[1]) { // command[1] is the command itself like add, delete and query-largest
                    case "add":// If the command is add
                        Watcher watcher = new Watcher(Double.parseDouble(command[2]), Double.parseDouble(command[3]), command[4]); // Create a new Watcher object with longitude, latitude and name
                        watcherlist.addLast(watcher); // Add watcher to the list
                        watcherQueue.dequeue();//  Remove action from the queue
                        System.out.println(watcher + " is added to the watcher-list\n");
                        break;
                    case "delete":
                        watcherlist.findAndRemove(command[2]); // Find and remove the watcher from the list
                        watcherQueue.dequeue(); // Remove the action from the queue
                        break;
                    case "query-largest":
                        earthquakelist.queryLargest(); // Find and return the largest earthquake in the last 6 hours
                        watcherQueue.dequeue();
                        break;
                    default:
                        break;
                }
            }
            else {
                break; // Break the loop when the time of the next option does not equal the current time of the simulation
            }
            
        }
        while (!earthquakeQueue.isEmpty() && earthquakeQueue.first().getTime() == time) { // A while loop to ensure all commands are processed
            Earthquake recent = earthquakeQueue.dequeue(); // Remove the earthquake from the queue
            earthquakelist.addLast(recent); // Add the earthquake to the list
            System.out.println(recent + " is inserted into the earthquake-list");
            watcherlist.closeWatchers(recent);  // Notify all watchers that are close to the earthquake
        }
        time+=1; // Increase the simulation time to advance the loop
    }
}
    
    // This method loads and reads the input file for watchers actions, and puts them in a queue in order
    public static ArrayQueue<String> readWatchers(File file){
        ArrayQueue <String> queue = new ArrayQueue<>(); // Create a queue to add the actions to
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) { // Read line by line
                String line = scanner.nextLine().trim();
                if(!line.isBlank()) { // Ignore empty lines
                    queue.enqueue(line); // Add action to the queue
                }
            }
            scanner.close();            
        } catch (FileNotFoundException e) { // In case file is not found
            System.out.println("File not found.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An error happened: " + e.getMessage());
        }
        return queue;
        
    }
    
    // This method loads and reads the input file for earthquakes and puts them in a queue in order
    public static ArrayQueue<Earthquake> readEarthquakes(File file) {
        ArrayQueue <Earthquake> queue = new ArrayQueue<>(); // Create a queue to add the earthquakes to
        try {
            Scanner scanner = new Scanner(file);
            int id = 0; int time = 0; String place = ""; double longitude = 0.0; double latitude = 0.0; double depth = 0.0; double magnitude = 0.0; // Default values
            while (scanner.hasNextLine()) { // Read line by line
                String line = scanner.nextLine().trim();
                if (line.startsWith("<id>")) { // Reads the id of the earthquake
                    int start = line.indexOf(">") + 1; // To seperate the opening and closing tags from the value
                    int end = line.indexOf("</");
                    String content = line.substring(start, end).trim(); // Gets the value itself
                    id = Integer.parseInt(content); // Parse to int so it can be used to create an earthquake object
                }
                if (line.startsWith("<time>")) { // Reads the time of the earthquake
                    int start = line.indexOf(">") + 1; // To seperate the opening and closing tags from the value
                    int end = line.indexOf("</");
                    String content = line.substring(start, end).trim(); // Gets the value itself
                    time = Integer.parseInt(content); // Parse to int so it can be used to create an earthquake object
                }
                
                else if (line.startsWith("<place>")) { // Reads the place of the earthquake
                    int start = line.indexOf(">") + 1; // To seperate the opening and closing tags from the value
                    int end = line.indexOf("</");
                    place = line.substring(start, end).trim();
                }

                else if (line.startsWith("<coordinates>")) { // Reads the coordinates of the earthquake
                    int start = line.indexOf(">") + 1;
                    int end = line.indexOf("</");
                    String content = line.substring(start, end).trim();
                    String[] tokens = content.split(","); // Seperate longitude, latitude and depth by commas
                    longitude = Double.parseDouble(tokens[0].trim());
                    latitude = Double.parseDouble(tokens[1].trim());
                    depth = Double.parseDouble(tokens[2].trim());
                }
            
                else if (line.startsWith("<magnitude>")) { // Reads the amgnitude of the earthquake
                    int start = line.indexOf(">") + 1; // To seperate the opening and closing tags from the value
                    int end = line.indexOf("</");
                    String content = line.substring(start, end).trim();
                    magnitude = Double.parseDouble(content); // Parse to double so it can be used to create an earthquake object
                }

                else if (line.startsWith("</earthquake>")) { // Reads the closing tag to know there is no more info
                    Earthquake earthquake = new Earthquake(id, time, place, longitude, latitude, depth, magnitude);
                    queue.enqueue(earthquake);
                    id +=1;
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found."); // In case file is not found
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An error happened: " + e.getMessage());
        }
        return queue;
    }

    
}
