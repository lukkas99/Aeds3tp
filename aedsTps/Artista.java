import java.util.Date;

class Artista {

    // atributos da classe
    private int               id;
    private String            artist;
    private String            country;
    private String            periodActive; 
    private Date              year;
    private int               nmbrGenre; //Quantidade de generos
    private String[]          genre;
    private float             tcu;   //Total Certified Units
    private float             sales; //Claimed sales of the records

    // construtores 
    public Artista() {
        this.id     = 0;
        this.artist  = "";
        this.country  = "";
        this.periodActive  = "";
        this.year = new Date();
        this.nmbrGenre   = 0;
        this.genre       = new String[nmbrGenre];
        this.tcu  = 0;
        this.sales  = 0;

    }

    public Artista(int id, String artist, String country, String periodActive,Date year, 
                 int nmbrGenre, String[] genre, float tcu, float sales){

        setId(id);
        setArtist(artist);
        setCountry(country);
        setPeriodActive(periodActive);
        setYear(year);
        setNmbrGenre(nmbrGenre);
        setGenre(genre);
        setTcu(tcu);
        setSales(sales);
    }

    // getters e setters:
    // ID
    public void setId(int id) {this.id = id;}
    public int getId() {return this.id;}

    //Artista
    public void setArtist(String artist) {this.artist = artist;}
    public String getArtist() {return this.artist;}

    //país origem
    public void setCountry(String country) {this.country = country;}
    public String getCountry() {return this.country;}

    //periodo de atividade
    public void setPeriodActive(String periodActive) {this.periodActive = periodActive;}
    public String getPeriodActive() {return this.periodActive;}

    //ano de fundacao
    public void setYear(Date year) {this.year = year;}
    public Date getYear() {return this.year;}

    //quantidade de generos
    public void setNmbrGenre(int nmbrGenre) {this.nmbrGenre = nmbrGenre;}
    public int getNmbrGenre() {return nmbrGenre;}

    //lista de genero
    public void setGenre(String[] genre) {this.genre = genre;}
    public String[] getGenre() {return this.genre;}

    //tcu
    public void setTcu(float tcu) {this.tcu = tcu;}
    public float getTcu() {return this.tcu;}

    //vendas
    public void setSales(float sales) {this.sales = sales;}
    public float getSales() {return this.sales;}

    // printar na tela
    public Artista print() {
        return (this);
    }


}
