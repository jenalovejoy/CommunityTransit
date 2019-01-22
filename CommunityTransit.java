import java.util.*;
import java.io.*;

public class CommunityTransit {

    PageScanner pageScan;

    public CommunityTransit(String input) throws Exception {
        pageScan = new PageScanner(input);
    }

    public void start(){

        System.out.println("Welcome to CommunityTransit!");
        System.out.println("Located at \"https://www.communitytransit.org/busservice/schedules/\"");
        String letter = getLetter();

        while (!letter.equals("!")){ 
            ArrayList<String> availableRoutes = pageScan.findDestinations(letter);
            String route = getRouteNumber(availableRoutes);
            pageScan.showRouteDetails(route);
            letter = getLetter();
        }

        System.out.println("Thank you for using CommunityTransit! Goodbye");

    }
    
    public String getLetter(){
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String output = "";

        while (!valid){
            System.out.print("Please enter a letter that your destinations start with, or \"!\" to quit: ");
            
            input = scanner.nextLine();
            System.out.println(input);
            int value = (int) input.charAt(0);

            // Comparing if input has an ASCII value for an uppercase or lowercase letter
            boolean isLetter = (value >= 65 && value <= 90) | (value >= 97 && value <= 122);

            if (input.equals("!") | (input.length() == 1 && isLetter)){
                output = input.toUpperCase();
                valid = true;
            } else {
                System.out.println("Input invalid, please try again");
            }
            
        }
        return output;
    }

    public String getRouteNumber(ArrayList<String> availableRoutes){

        boolean valid = false;
        String input = "";

        Scanner scanner = new Scanner(System.in);

        while (!valid){
            System.out.print("Please enter an available route number, or \"!\" to quit: ");

            input = scanner.nextLine();

            if (input.equals("!") | (isValidRoute(input) && availableRoutes.contains(input))){
                valid = true;

            } else {
                System.out.println("Input invalid, please enter route exactly as listed");
                System.out.println("This may include using the shown \"/\"");
            }
        }
        return input;
    }


    public boolean isValidRoute(String input){

        int length = input.length();
        int value = 0;

        // 48 to 57 are integers, 47 is '/' which is used for multiple routes
        for (int i = 0; i < length; i++){
            value = (int) input.charAt(i);
            if (value > 57 | value < 47){
                return false;
            }
        }
        return true;
    }

    

}