import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
import java.net.*;
import java.io.*;

public class PageScanner {

    URL url;
    String pageText;

    public PageScanner(String _url) throws IOException {

        // Creates connection, and build the page
        url = new URL(_url);
        URLConnection conn = url.openConnection();
        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));


        while (input.ready()) {
            pageText += input.readLine();
        }
    }

    public ArrayList<Destination> findDestinations(char c){
        ArrayList<Destination> destinations = new ArrayList<>();
        String findDest = "<h3>(" + c + ".+)<\\h3>";
        Pattern p = Pattern.compile(findDest);
        Matcher m = p.matcher(pageText);

        while (m.find()){
            System.out.println(m.group(1));
        }
        return destinations;
    }

//    public ArrayList<int> findRoutes(String destination)
//
//    public ArrayList<>

}
