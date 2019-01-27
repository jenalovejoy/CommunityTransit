// Programming Languages - Assignment 1
// Jena Lovejoy
// CommunityClient starts the interaction between the user and the CommunityTransit website

import java.util.*;
import java.io.*;

public class CommunityClient {
    public static void main(String[] args) throws Exception {

        String input = "https://www.communitytransit.org/busservice/schedules/";        
        CommunityTransit transit = new CommunityTransit(input);
        transit.start();
        
    }
}