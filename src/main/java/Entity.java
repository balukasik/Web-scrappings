public class Entity {
    private String spadek;
    private String pozycja;
    private String notowania;
    private String najwyzej;
    private String nazwa,wykonawca,producent;
    private String kategoria;


    public Entity(String spadek, String pozycja, String notowania, String najwyzej, String nazwa, String wykonawca, String producent,String kategoria) {
        this.spadek = spadek;
        this.pozycja = pozycja;
        this.notowania = notowania;
        this.najwyzej = najwyzej;
        this.nazwa = nazwa;
        this.wykonawca = wykonawca;
        this.producent = producent;
        this.kategoria = kategoria;
    }

    @Override
    public String toString(){
        return spadek + "; " + pozycja+ "; " + notowania+ "; " + najwyzej+ "; " + nazwa+ "; " + wykonawca+ "; " + producent + "; "+ kategoria;
    }
}
