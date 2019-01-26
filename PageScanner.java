import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

// import com.sun.jndi.ldap.Connection;

import java.net.*;
import java.io.*;

public class PageScanner {

    URL url;
    String pageText;

    public PageScanner(String _url) throws Exception {

        // Creates connection, and build the page
        url = new URL(_url);
        URLConnection conn = url.openConnection();
        conn.addRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System.out.println("Scanner built");
        String line = "";
        while (input.readLine() != null) {
            line = input.readLine();
            pageText += line + "\n";
        }

        System.out.println(pageText);
                
        input.close();
    }

    public ArrayList<Destination> findDestinations(String c){
        ArrayList<Destination> destinations = new ArrayList<>();
        // c = c.toLowerCase();
        // String findDest = "<h3>(" + c + ".+)</h3>";
        String findDest = "<hr id=\"(" + c + ".+)\"";
        // String findRoute = "((\s.*\s.*\s.*<a href.*>(\d{3}(\W\d{3})?).*\s.*\s.*\s.*))"; -> want group 3
        String findRoute = ">(\\d{3}.*(\\W\\d{3})?).?.?</a>";
        String findRouteEnd = "<hr id=";

        Pattern pDestination = Pattern.compile(findDest);
        Matcher mDestination = pDestination.matcher(pageText);

        Pattern pFindRoute = Pattern.compile(findRoute);
        Matcher mFindRoute = pFindRoute.matcher(pageText);

        Pattern pFindRouteEnd = Pattern.compile(findRouteEnd);
        Matcher mFindRouteEnd = pFindRouteEnd.matcher(pageText);

        int count = 0;
        while (mDestination.find()){
            ArrayList<String> availableRoutes = new ArrayList<>();
            String destination = mDestination.group(1);
            destination = destination.substring(0,1).toUpperCase() + destination.substring(1);
            int current = mDestination.start();
            mFindRouteEnd.find(current + 1);
            int stop = mFindRouteEnd.start();
            mFindRoute.region(current, stop);

            while (mFindRoute.find()){
                String route = mFindRoute.group(1);
                availableRoutes.add(route);
            }

            destinations.add(new Destination(destination, availableRoutes)); 
        }

        return destinations;
    }

    public String getRouteLink(String route){
        String routeLink = "<a href=\"(.*\\d{3})\"\\s?.*>" + route + "</a>";

        Pattern pRouteLink = Pattern.compile(routeLink);
        Matcher mRouteLink = pRouteLink.matcher(pageText);
         
        mRouteLink.find();
        return mRouteLink.group(1);
    }

    public ArrayList<String> getRouteInformation(String route){
        
        ArrayList<String> routeInformation = new ArrayList<String>();
        String findRoute = "<small>(.*)</small>";
        String destination = "Destination: ";

        // Managing the overall route
        Pattern pFindRoute = Pattern.compile(findRoute);
        Matcher mFindRoute = pFindRoute.matcher(pageText);
        mFindRoute.find();
        destination += mFindRoute.group(1);

        routeInformation.add(destination);

        // Managing individual stops
        String findStops = "(\\w)</strong>\\s*<p>(.*)</p>";
        Pattern pFindStops = Pattern.compile();
        Matcher mFindStops = pFindStops.matcher(pageText);

        while (mFindStops.find()){
            String stopNum = mFindStops.group(1);
            String stop = mFindStops.group(2);
            String formattedStop = "Stop " + stopNum + ": ";
            int index = stop.indexOf("&amp;");

            if (index > -1){
                formattedStop += stop.substring(0, index) + "&" + stop.substring(index+4);
            } else {
                formattedStop = stop;
            }
            
            routeInformation.add(formattedStop);
        }

        return routeInformation;
    }

}
