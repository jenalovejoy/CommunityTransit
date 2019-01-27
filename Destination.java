// Programming Languages - Assignment 1
// Jena Lovejoy
// Destination represents an individual location and all associated routes

import java.util.*;
import java.io.*;

public class Destination {
    String location;
    ArrayList<String> routes;

    public Destination(String _location, ArrayList<String> _routes){

        if (_location.contains("-")){
            int index = _location.indexOf("-");
            location = _location.substring(0,index) + " " + _location.substring(index + 1, index + 2).toUpperCase() + _location.substring(index+2);
        } else {
            location = _location;
        }

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

    // Verifies if a particular route goes to/from this destination
    public boolean validRoute(String route){
        for (String r : routes){
            if (r.toString().equals(route.toString())){
                return true;
            }
        }
        return false;
    }
}
