import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class ImportCSV extends CRUD{

    // Cria um array list de Strings a partir da leitura do arquivo passado como parametro
    public static List<String[]> readCsv(String filename) throws IOException{
        List<String[]> dataCsv = new ArrayList<>();
        //abre o arquivo filename
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        //le a primeira linha do arquivo
        reader.readLine();
        //Cria uma String line para ler cada linha do arquivo dentro da estrutura de repetição while
        String line;
        //Enquanto existir conteudo a ser lido continua
        while ((line = reader.readLine()) != null){
            //chama metodo para tratar Strings
            line = treatString(line);
            
            //divide a linha em um array separando por virgula
            String[] split1 = line.split(",");
            
            //adiciona campo no array list dataCSV
            dataCsv.add(split1);
        }
        reader.close(); //fecha arquivo.csv
        return dataCsv; //retorna arraylist
    }

    //Trata linhas do arquivo que possuem conteudo(period_active) entre aspas "", ex: line9.
    private static String treatString (String line){
        //transforma a linha em um vetor de caracteres
        char [] chars = line.toCharArray();
        //Cria uma variável "botão".
        boolean entreaspas = false;
        //nova String vazia.
        String newline = "";

        //repete enquanto i < tamanho do vetor de chars
        for(int i = 0; i < chars.length;i++){
            //se char for aspas
            if (chars[i] == '\"'){
                //"botão = true"
                entreaspas = !entreaspas;
            }
            //se char for virgula e botão = true
            else if(chars[i] == ',' && entreaspas){
                newline += ""; //ignora se tiver virgula entre as aspas
            }
            else{
                newline += chars[i]; // adiciona o caractere normalmente
            } 
        }
        //retorna String tratada
        return (newline.toString());
    }

    public static int lastId(String filename) throws IOException{
    int lastId = 0;
    //abre o arquivo filename
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    //le a primeira linha do arquivo
    reader.readLine();
    //Enquanto existir conteudo a ser lido continua
    while ((reader.readLine()) != null){
     //chama metodo para tratar Strings
        lastId++;
    }
    reader.close(); //fecha arquivo.csv
    return lastId; //retorna arraylist
    }

    /*metodo que recebe como parametro String com nome do arquivo de bytes (bsa.bin)
    onde vai ser salvo os registros da lista de Strings dataCSV */
    public static void toBin(RandomAccessFile raf, List<String[]> dataCsv, int testeId) throws IOException{

        //grava a quantidade de registros = tamanho da lista de Strings
        raf.writeInt(testeId);
        //Cria uma nova lista de Strings "dataline" para cada "Linha" da lista de Strings
        for (String[] dataline : dataCsv){
            
            int qntGeneros = 0;//variavel para armazenar quantidade de generos musicais
            int a = 0, b = 0, c = 0;//variavel para controle de repeticao
            float tcu = 0; //total de vendas certificadas
            float salesA = 0, salesB = 0, ab = 0;//variavel para somar vendas
            int genreSize = 0;//tamanho das Strings de generos em bytes

            //se dataline[5]= qntGeneros não está vazia:
            if (!dataline[5].isEmpty()) {
                qntGeneros = Integer.parseInt(dataline[5]);//atribui valor da String a variavel
                a = qntGeneros;
            }
            
            //se String da posicao 6(primeiro genero) + qnt de generos = String tcu, tratar ela trocando million por nada
            dataline[6+qntGeneros] = dataline[6+qntGeneros].replace("million","");
            tcu = Float.parseFloat(dataline[6+qntGeneros]);

            //trata a proxima String sales
            dataline[7+qntGeneros] = dataline[7+qntGeneros].replace("million","");
            
            //b = tamanho da String sales
            b = dataline[7+qntGeneros].length();

            //se b >= 7 Essa String possui 2 valores float e eles sao somados
            if (b >= 7){

                //Cria Strings para armazenar os dois valores
                String firstSum = "";
                String secondSum = "";

                //Estrutura de repeticao para adicionar o valor as Strings
                for ( int i = 0; i <b; i++){   
                    if(i < 3){
                        firstSum += dataline[7+qntGeneros].charAt(i);
                    }
                    if(i >= 3 && i < b){
                        secondSum += dataline[7+qntGeneros].charAt(i);
                    }
                }

                //passa String para tipo float
                salesA = Float.parseFloat(firstSum);
                salesB = Float.parseFloat(secondSum);

                ab = salesA + salesB;
            }
            //se não adiciona unico valor 
            else{
                ab = Float.parseFloat(dataline[7+qntGeneros]);
            }
            
            raf.seek(raf.length()); // Posiciona o ponteiro no final do arquivo
            raf.writeByte(0); // Escreve a lapide (0 = ativo)
            
            //-----------------somar tamanho em bytes--------------------
            //enquanto quantidade de generos musicais dataline[5]> 0
            while (a > 0){
                //soma os tamanhos de cada genero em bytes.
                genreSize += dataline[5+a].length();
                a--;
            }
            int sizeInBytes = Float.BYTES;

            //soma tamanho de cada String do arranjo em bytes
            int recordSize = dataline[0].length() + dataline[1].length() + dataline[2].length()
            + dataline[3].length() + dataline[4].length() + dataline[5].length() + genreSize + sizeInBytes + sizeInBytes + 2*(7+qntGeneros) ;
            //soma vendas, tcu somado e Strings
            float valorFloat = tcu + ab;
            //soma tudo
            float resultado = recordSize + valorFloat;
            
            //escreve tamanho do registro em bytes no arquivo
            if (Integer.parseInt(dataline[0]) >= 10 && Integer.parseInt(dataline[0]) < 100){
                raf.writeInt(recordSize-1);
            }
            else if(Integer.parseInt(dataline[0]) >= 100){
                raf.writeInt(recordSize-2);
            }
            else{
                raf.writeInt(recordSize); 
            }
            //----------------------------------------------------------

            //------------------ gravar campos no raf.bin----------------------
            //aloca espaco para um inteiro no arquivo de dados para ID
            byte[] intid = ByteBuffer.allocate(4).putInt(Integer.parseInt(dataline[0])).array();
            raf.write(intid); //grava o primeiro campo (id) como um int no arquivo de dados

            //raf.writeInt(Integer.parseInt(dataline[0])); //grava o primeiro campo (id) como um inteiro no arquivo de dados
            // System.out.println("id:"+dataline[0]);
            ultimoId = Integer.parseInt(dataline[0]);
            // System.out.println("artista:"+dataline[1]);
            raf.writeUTF(dataline[1]); //grava o segundo campo (artist) como uma String no arquivo de dados
            
            // System.out.println("pais:"+dataline[2]);
            raf.writeUTF(dataline[2]); //grava o terceiro campo (Country) como uma String no arquivo de dados
            
            // System.out.println("periodActive:"+dataline[3]);
            raf.writeUTF(dataline[3]); //grava o quarto campo (period_active) como uma String no arquivo de dados
            
            // System.out.println("foundedIn:"+dataline[4]);
            raf.writeUTF(dataline[4]); //grava o quinto campo (Year) como uma String no arquivo de dados
            
            //aloca espaco para um inteiro no arquivo de dados para qnt de generos
            byte[] intBytes = ByteBuffer.allocate(4).putInt(qntGeneros).array();
            raf.write(intBytes); //grava o sexto campo (qntGeneros) como um int no arquivo de dados

            //enquanto qntgeneros for maior que 0 vai salvando cada genero no arquivo de dados
            while(qntGeneros > 0){
                raf.writeUTF(dataline[6+c]);
                c++;
                qntGeneros--;
            }

            //grava tcu e vendas no arquivo
            raf.writeFloat(tcu);
            raf.writeFloat(ab);
            
        }
    }
}
