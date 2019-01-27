// Programming Languages - Assignment 1
// Jena Lovejoy
// CommunityTransit interacts with the information given by PageScanner and facilitates the interaction between that data and the user

import java.util.*;
import java.io.*;

public class CommunityTransit {

    PageScanner landingPageScan;

    public CommunityTransit(String input) throws Exception {
        landingPageScan = new PageScanner(input);
    }

    // Manages user input and directs the flow of interactions
    public void start() throws Exception {
        buffer(24);
        System.out.println("Welcome to CommunityTransit!");
        System.out.println("Located at \"https://www.communitytransit.org/busservice/schedules/\"");
        buffer(24);
        String destinationLetter = getDestinationLetter();
        
        while (!destinationLetter.equals("!")){ 
            // Tracks destinations beginning with a chosen letter
            ArrayList<Destination> destinations = displayDestinations(destinationLetter);
            
            // If those destinations exist, accept further input
            if (destinations.size() != 0){
                String routeNumber = getRouteNumber(destinations); 
                while (!routeNumber.equals("!")){
                    displayRouteInformation(routeNumber);
                    routeNumber = getRouteNumber(destinations); 
                }
            } else {
                System.out.println("No destinations starting with " + destinationLetter + " are available.");
            }
            destinationLetter = getDestinationLetter();
            buffer(24);
        }

        System.out.print("Thank you for using CommunityTransit! Goodbye\n");
        buffer(24);
    }
    
    // Obtains a valid input for finding the desired destinations 
    public String getDestinationLetter(){
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String output = "";

        while (!valid){
            System.out.print("Please enter a letter that your destination starts with, or \"!\" to quit: ");
            
            input = scanner.nextLine();
            int value = (int) input.charAt(0);

            // Comparing if input has an ASCII value for an uppercase or lowercase destinationLetter
            boolean isLetter = (value >= 65 && value <= 90) | (value >= 97 && value <= 122);

            if (input.equals("!") | (input.length() == 1 && isLetter)){
                output = input.toLowerCase();
                valid = true;
            } else {
                System.out.println("Input invalid, please enter a letter");
            }
        }
        return output;
    }

    // Obtains a valid input for finding the desired route 
    public String getRouteNumber(ArrayList<Destination> destinations){

        boolean valid = false;
        String input = "";

        Scanner scanner = new Scanner(System.in);

        while (!valid){
            buffer(24);
            System.out.print("Please enter an available route number, or \"!\" to go back: ");

            input = scanner.nextLine();

            if (input.equals("!") | isValidRoute(input, destinations)){
                valid = true;

            } else {
                System.out.println("Input invalid, please enter route exactly as listed");
                System.out.println("This will include any special characters");
            }
        }
        return input;
    }

    // Verifies if the input is an available route
    public boolean isValidRoute(String input, ArrayList<Destination> destinations){
        for (Destination d : destinations){
            if (d.validRoute(input)){
                return true;
            }
        }
        return false; 
    }

    // Prints all destinations available
    public ArrayList<Destination> displayDestinations(String c){
        ArrayList<Destination> destinations = landingPageScan.findDestinations(c);

        for (Destination d : destinations){
            buffer(24);
            System.out.print(d);

        }
        return destinations;
    }

    // Finds and prints all stop and week information for a givene route
    public String displayRouteInformation(String route) throws Exception {

        String smallRouteLink = landingPageScan.getRouteLink(route);
        String routeLink = "https://www.communitytransit.org/busservice" +  smallRouteLink;

        System.out.println("the link to your route is: " +  routeLink);
        System.out.println("     routes loading . . .");
        buffer(24);
        
        // Extracts the information for the given webpage
        PageScanner routePage = new PageScanner(routeLink);
        ArrayList<String> routeInformation = routePage.getRouteInformation();

        // Examines given information and formats accordingly
        for (String s : routeInformation){
            if (s.contains("destination")){
                System.out.println(s);
            } else if (s.contains("+")){
                buffer(24);
            } else {
                System.out.println(s);
            }
        }
        return routeLink;
    }

    // Adds a stylized line between outputs
    public void buffer(int w){
        System.out.println();

        for(int i = 0; i <= w; i++){
            System.out.print("+ ");
        }
        System.out.println();
        System.out.println();
    }
}