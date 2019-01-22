import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
import java.net.*;
import java.io.*;

public class PageScanner {

    // URL url;
    String pageText;

    public PageScanner(String input) throws Exception {

        File schedules = new File(input);
        Scanner s = new Scanner(schedules);
        while (s.hasNextLine()) {
        		pageText += s.nextLine();
        		pageText +="\n";
        }
        // // Creates connection, and build the page
        // url = new URL(_url);
        // URLConnection conn = url.openConnection();
        // conn.connect();
        
        // BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        // System.out.println("Scanner built");
        // String line = "";
        // while (input.readLine() != null) {
        //     line = input.readLine();
        //     pageText += line + "\n";
        // }
        
        // System.out.println(pageText);
        
        // input.close();
    }

    public ArrayList<String> findDestinations(String c){
        ArrayList<String> availableRoutes = new ArrayList<>();
        String findDest = "<h3>(" + c + ".+)</h3>";
        // String findRoute = "((\s.*\s.*\s.*<a href.*>(\d{3}(\W\d{3})?).*\s.*\s.*\s.*))"; -> want group 3

        Pattern p = Pattern.compile(findDest);
        Matcher m = p.matcher(pageText);
        
        while (m.find()){
            String destination = m.group(1);
            // String route = m.group(1);
            availableRoutes.add(destination); // say when no routes are available

            System.out.println("Destination: " + destination);
            // System.out.println("Bus number: " + route);    
        }

        return availableRoutes;
    }

    public void showRouteDetails(String route){


    }

    

//    public ArrayList<String> findRoutes(String destination)
//
//    public ArrayList<>

}
