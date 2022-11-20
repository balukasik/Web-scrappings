package Movies;

public class Location {
    private final String name;
    private final String url;
    private String address;
    private String city;
    private Cinema cinema;



    public Location(String name, String url, String address, String city, Cinema cinema){
        this.name = name;
        this.url = url;
        this.address = address;
        this.city = city;
        this.cinema = cinema;

    }

    public Location(String name, String url, Cinema cinema) {
        this.name = name;
        this.url = url;
        this.cinema = cinema;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setAddressData(String address, String city){
        this.address = address;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getCity() {
        return city;
    }
}
