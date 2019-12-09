package sample;

public class Rozklad {

    private String gruppa;
    private String predm;
    private int aud;
    private Time time;

    public Rozklad(String gruppa, String predm, int aud, Time time) {
        this.gruppa = gruppa;
        this.predm = predm;
        this.aud = aud;
        this.time = time;
    }

    public String getGruppa() { return gruppa; }

    public String getPredm() { return predm; }

    public int getAud() { return aud; }

    public Time getTime() { return time; }
}
