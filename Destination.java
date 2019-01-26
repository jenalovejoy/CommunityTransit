import java.util.*;
import java.io.*;

public class Destination {
    String location;
    ArrayList<String> routes;

    public Destination(String _location, ArrayList<String> _routes){
        location = _location;
        routes = new ArrayList<String>(_routes);

    }

    public String toString(){
        String output = "";
        output += "Destination: " + location + "\n";
        for (String r : routes){
            output += "Bus number: " + r + "\n";

        }
        return output;
    }

    public boolean validRoute(String route){
        for (String r : routes){
            if (r.toString().equals(route.toString())){
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return location;
    }

    // public boolean notAvailable(){
    //     return location.equals("") | routes.length() == 0;

    // }
}
