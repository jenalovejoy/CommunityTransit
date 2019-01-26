import java.util.*;
import java.io.*;

public class CommunityTransit {

    PageScanner landingPageScan;

    public CommunityTransit(String input) throws Exception {
        landingPageScan = new PageScanner(input);
    }

    public void start() throws Exception {
        buffer(15);
        System.out.println("Welcome to CommunityTransit!");
        System.out.println("Located at \"https://www.communitytransit.org/busservice/schedules/\"\n");

        String destinationLetter = getDestinationLetter();
        
        while (!destinationLetter.equals("!")){ 
            ArrayList<Destination> destinations = displayDestinations(destinationLetter);
            
            if (destinations.size() != 0){
                String routeNumber = getRouteNumber(destinations); 
                while (!routeNumber.equals("!")){
                    String routeLink = displayRouteInformation(routeNumber);
                    routeNumber = getRouteNumber(destinations); 
                }
            } else {
                System.out.println("No destinations starting with " + destinationLetter + " exist");
            }
            destinationLetter = getDestinationLetter();
            System.out.println("\n");
            buffer(15);
        }

        System.out.println("Thank you for using CommunityTransit! Goodbye\n");
        buffer(15);
    }
    
    public String getDestinationLetter(){
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String output = "";

        while (!valid){
            System.out.print("Please enter a letter that your destinations start with, or \"!\" to quit: ");
            
            input = scanner.nextLine();
            int value = (int) input.charAt(0);

            // Comparing if input has an ASCII value for an uppercase or lowercase destinationLetter
            boolean isLetter = (value >= 65 && value <= 90) | (value >= 97 && value <= 122);

            if (input.equals("!") | (input.length() == 1 && isLetter)){
                output = input.toLowerCase();
                valid = true;
            } else {
                System.out.println("Input invalid, please try again");
            }

        }
        return output;
    }

    public String getRouteNumber(ArrayList<Destination> destinations){

        boolean valid = false;
        String input = "";

        Scanner scanner = new Scanner(System.in);

        while (!valid){
            buffer(15);
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

    public boolean isValidRoute(String input, ArrayList<Destination> destinations){
        for (Destination d : destinations){
            if (d.validRoute(input)){
                return true;
            }
        }
        return false; 
    }

    public ArrayList<Destination> displayDestinations(String c){
        ArrayList<Destination> destinations = landingPageScan.findDestinations(c);

        for (Destination d : destinations){
            buffer(15);
            System.out.println(d);

        }
        return destinations;
    }

    public String displayRouteInformation(String route) throws Exception {
        
        // String routeLink = "";
        // PageScanner routePage;
        // try {
        //     String smallRouteLink = landingPageScan.getRouteLink(route);
        //     routeLink = "https://www.communitytransit.org/busservice" +  smallRouteLink;
        //     routePage = new PageScanner(routeLink);
        // } catch (Exception e){
        //     System.out.println("We're sorry, that page is no longer available");
        // }
        // System.out.println("the link to your route is: " +  routeLink);
        
        String smallRouteLink = landingPageScan.getRouteLink(route);
        String routeLink = "https://www.communitytransit.org/busservice" +  smallRouteLink;

        System.out.println("the link to your route is: " +  routeLink);
        
        PageScanner routePage = new PageScanner(routeLink);

        routePage.getRouteInformation();

        System.out.println("Destination: ");

        return routeLink;
    }

    public void buffer(int w){
        for(int i = 0; i <= w; i++){
            System.out.print("+ ");
        }
        System.out.println();
        System.out.println();
    }

    // public String adjustRoute(String route){
    //     if (route.contains(" ")){
    //         route = route.substring(0, route.indexOf(" ") - 1);
    //     }
    //     if (route.contains("/")){
    //         int index = route.indexOf("/");
    //         String first = route.substring(0, index);
    //         String second = route.substring(index + 1);
    //         route = first + "-" + second;
    //         System.out.println(route);
    //     }

    //     return route;

    // }
}