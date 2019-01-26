import java.util.*;
import java.io.*;

public class CommunityClient {

    public static void main(String[] args) throws Exception {

        String input = "https://www.communitytransit.org/busservice/schedules/";
        // String input = "schedules.html";
        
        CommunityTransit transit = new CommunityTransit(input);
        transit.start();

    }
}