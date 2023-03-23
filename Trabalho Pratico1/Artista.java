import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;

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

    // Converte objeto para um array de bytes
    public byte[] toByteArray() throws IOException { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Cria um array de bytes
        DataOutputStream dos = new DataOutputStream(baos); 

        // Escreve os dados da conta no array de bytes
        dos.writeInt(this.getId());    
        dos.writeUTF(this.getArtist());
        dos.writeUTF(this.getCountry()); 
        dos.writeUTF(this.getPeriodActive()); 
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String data = formatador.format(this.getYear());
        dos.writeUTF(data); 
        dos.writeInt(this.getNmbrGenre()); 
        for(int i = 0; i < this.getNmbrGenre(); i++){ 
            dos.writeUTF(this.getGenre()[i]);
        }
        dos.writeFloat(this.getTcu());
        dos.writeFloat(this.getSales());

        dos.close();
        baos.close();

        return baos.toByteArray(); // Retorna o array de bytes
    }

}
