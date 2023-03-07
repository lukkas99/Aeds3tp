import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;


public class ImportCSV {
    public static void main(String[]args) throws IOException{
        String binFile = "bsa.bin"; 
        List<String[]> dataCsv = readCsv("best_selling_artists_Date.csv");
        toBin(binFile, dataCsv);
        //System.out.println(dataCsv.get(2));
        //System.out.println(Arrays.toString(dataCsv.toArray()));
        // for(int i=0;i<dataCsv.size();i++){
        //     System.out.println(Arrays.toString(dataCsv.toArray()));
        // } 

    }

    private static List<String[]> readCsv(String filename) throws IOException{
        List<String[]> dataCsv = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null){
            //String teste = line;
           // int t = teste.length();
            //System.out.println("teste="+teste);
            line = line.replace("–","-"); //substituir character especial (en dash) por dash normal
            String[] dataline = line.split(",");
            dataCsv.add(dataline);
        }
        reader.close();
        return dataCsv;
    }

    private static void toBin(String binFile, List<String[]> dataCsv) throws IOException{
        FileOutputStream fos = new FileOutputStream(binFile);
        DataOutputStream dos = new DataOutputStream(fos);

        dos.writeInt(dataCsv.size());
        
        for (String[] dataline : dataCsv){
            //grava o primeiro campo (id) como um inteiro no arquivo de dados
            int intValue = 0;
            if(!dataline[0].isEmpty()){
                intValue = Integer.parseInt(dataline[0]);
            }
            byte[] intBytes = ByteBuffer.allocate(4).putInt(intValue).array();
            System.out.println("id:"+dataline[0]);
            dos.write(intBytes);
            dos.writeUTF(dataline[1]); //grava o segundo campo (artist) como uma String no arquivo de dados
            dos.writeUTF(dataline[2]); //grava o segundo campo (Country) como uma String no arquivo de dados
            if(!dataline[3].isEmpty()){
                String str = dataline[3];
                str = new String(str.getBytes(),"UTF-8");
                String replace = str.replace("?","-");
                System.out.println("datas:"+replace);
                // if(str.charAt(0)== '"'){
                    
                // }
                
                //dataline[6].replace(str,"");
            }
            dos.writeUTF(dataline[3]); //grava o segundo campo (period_active) como uma String no arquivo de dados
            dos.writeUTF(dataline[4]); //grava o segundo campo (Year) como uma String no arquivo de dados
            if(!dataline[6].isEmpty()){
                String str = dataline[6];
                String replace = str.replace("million","");
                System.out.println("tcu: "+replace); 
                //dataline[6].replace(str,"");
            }
            dos.writeUTF(dataline[5]); //grava o segundo campo (Genre) como uma String no arquivo de dados
            dos.writeUTF(dataline[6]);
            dos.writeUTF(dataline[7]);
            //dos.writeFloat(Float.parseFloat(dataline[6])); //grava o segundo campo (TCU) como um Float no arquivo de dados
            //dos.writeFloat(Float.parseFloat(dataline[7])); //grava o segundo campo (artist) como um Float no arquivo de dados
            //System.out.println(dataline[0]);
            //System.out.println(dataline[6]);
        }
        dos.close();
        fos.close();
    }
}
