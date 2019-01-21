import java.util.ArrayList;

public class Destination {
    String location;
    ArrayList<Integer> routes;

    public Destination(String _location, ArrayList<Integer> _routes){
        location = _location;
        routes = new ArrayList<Integer>(_routes);

    }

}
