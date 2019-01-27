// Programming Languages - Assignment 1
// Jena Lovejoy
// PageScanner parses a given website to extract particular information, specified through a regular expression

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
import java.net.*;
import java.io.*;

public class PageScanner {

    URL url;
    String pageText;

    // Opens the connection and reads the html
    public PageScanner(String _url) throws Exception {

        // Creates connection, and build the page
        url = new URL(_url);
        URLConnection conn = url.openConnection();
        conn.addRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        while ((line = input.readLine()) != null) {
            pageText += line + "\n";
        }
                
        input.close();
    }

    // Returns an ArrayList of all possible destinations
    public ArrayList<Destination> findDestinations(String c){
        ArrayList<Destination> destinations = new ArrayList<>();

        // Matches the destination name
        String findDest = "<hr id=\"(" + c + ".+)\"";
        Pattern pDestination = Pattern.compile(findDest);
        Matcher mDestination = pDestination.matcher(pageText);

        // Matches the route number
        String findRoute = ">(\\d{3}.*(\\W\\d{3})?).?.?</a>";
        Pattern pFindRoute = Pattern.compile(findRoute);
        Matcher mFindRoute = pFindRoute.matcher(pageText);

        // Matches the break in between destinations
        String findRouteEnd = "</div>\\s.*</div>\\s.*(<hr|<p><strong>)";
        Pattern pFindRouteEnd = Pattern.compile(findRouteEnd);
        Matcher mFindRouteEnd = pFindRouteEnd.matcher(pageText);

        // Extracts destinations
        while (mDestination.find()){
            // Tracks routes per destination
            ArrayList<String> availableRoutes = new ArrayList<>();
            String destination = mDestination.group(1);
            destination = destination.substring(0,1).toUpperCase() + destination.substring(1);

            // Determines bounds of search for routes
            int current = mDestination.start();
            mFindRouteEnd.find(current + 1);
            int stop = mFindRouteEnd.start();
            mFindRoute.region(current, stop);

            // Extracts routes
            while (mFindRoute.find()){
                String route = mFindRoute.group(1);
                availableRoutes.add(route);
            }

            // Stores the destination and route information
            destinations.add(new Destination(destination, availableRoutes)); 
        }

        return destinations;
    }

    // Obtains the link to a particular route's site
    public String getRouteLink(String route){
        if (route.contains("*")){
            route = route.substring(0, route.indexOf("*")) + "\\*";
        }
        String routeLink = "<a href=\"(.*)\">" + route + "</a>";

        Pattern pRouteLink = Pattern.compile(routeLink);
        Matcher mRouteLink = pRouteLink.matcher(pageText);
         
        mRouteLink.find();
        return mRouteLink.group(1);
    }

    // Returns all destination and stop information for a particular route
    public ArrayList<String> getRouteInformation(){
        
        ArrayList<String> routeInformation = new ArrayList<String>();

        // Managing the overall route
        String findRoute = "<h2>(.*)<small>(.*)</small>";
        Pattern pFindRoute = Pattern.compile(findRoute);
        Matcher mFindRoute = pFindRoute.matcher(pageText);

        // Managing individual stops
        String findStops = "(\\w)</strong>\\s.*\\s.*<p>(.*)</p>";
        Pattern pFindStops = Pattern.compile(findStops);
        Matcher mFindStops = pFindStops.matcher(pageText);

        // Mangaging where sections of stops (ie per time of week or direction) end
        String limit = "</thead>";
        Pattern pLimit = Pattern.compile(limit);
        Matcher mLimit = pLimit.matcher(pageText);

        // Extracts time of week and destination
        while (mFindRoute.find()){
            String timeFrame = mFindRoute.group(1);
            String destination = mFindRoute.group(2);

            // adjusts formatting
            int index = destination.indexOf("&amp;"); 
            String formattedDestination = timeFrame + " destination: ";
            if (index > -1){
                formattedDestination += destination.substring(0, index) + "&" + destination.substring(index+5);
            } else {
                formattedDestination += destination;
            }

            routeInformation.add(formattedDestination);

            // Sets bounds between sections of stops 
            int start = mFindRoute.start();
            mLimit.find(start);
            int end = mLimit.start();
            mFindStops.region(start, end);

            // Extracts individual stops
            while (mFindStops.find()){
                String stopNum = mFindStops.group(1);
                String stop = mFindStops.group(2);

                // Adjusts formatting
                String formattedStop = "    Stop " + stopNum + ": ";
                index = stop.indexOf("&amp;");
                if (index > -1){
                    formattedStop += stop.substring(0, index) + "&" + stop.substring(index+5);
                } else {
                    formattedStop += stop;
                }
                routeInformation.add(formattedStop);
            }
            routeInformation.add("+"); // to track sections
        }
        return routeInformation;
    }
}
