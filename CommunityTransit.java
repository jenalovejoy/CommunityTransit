public class CommunityTransit {

    public static void main(String[] args){

        String site = "https://www.communitytransit.org/busservice/schedules/";

        try{
            PageScanner parser = new PageScanner(site);
        } catch (Exception e) {

        }

        parser.findDestinations('A');

//        ArrayList<String> destinations = parser.findDestinations('A');
//
//        for (String d : destinations){
//            System.out.println(d);
//
//        }


    }

}