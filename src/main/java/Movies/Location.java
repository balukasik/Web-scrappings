package Movies;

public class Location {
    private final String name;
    private final String url;
    private String address;
    private String city;

    public Location(String name, String url, String address, String city){
        this.name = name;
        this.url = url;
        this.address = address;
        this.city = city;
    }

    public Location(String name, String url) {
        this.name = name;
        this.url = url;
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
