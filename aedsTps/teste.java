import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class teste {
    public static void main (String[]args){
        String linha1 = "9,Pink Floyd,United Kingdom,\"1965–1996, 2005, 2012–2014\",16/06/1967,Progressive rock / psychedelic rock,123.8 million,250 million200 million";
        linha1 = treatString(linha1);
        String[] split1 = linha1.split(",");
        for(int i = 0; i < split1.length; i++){
            System.out.println("split["+i+"]: "+split1[i]);
        }
    }

    private static String treatString (String line){
        int aspas = 0, subegin = 0, subend = 0;
        String preaspas = "", entreaspas = "", posaspas = "", tratado = "";
        line = line.replace("–","-");
        System.out.println("Linha 1: "+line);
        for(int i = 0; i < line.length();i++){
            if (line.charAt(i) == '"'){
                subend = i;
                aspas++;
                posaspas = posaspas + line.charAt(i);
                //System.out.println("posicao:"+i+" = "+line.charAt(i));
            }
            if (aspas == 1 && line.charAt(i) == '"'){
                subegin = i;
                preaspas = preaspas +line.charAt(i);
            }
            //while (aspas == 1){
                //System.out.println("é 1");
                // if (line.charAt(i) == ','){
                //     System.out.println("posicao , :"+i+" = "+line.charAt(i));
                // }
                // if( line.charAt(i) == '"'){
                //     aspas++;
                // }
            //}  
        }
        for ( int y= subegin+1; y < subend; y++){
            entreaspas = entreaspas + line.charAt(y);
        }
        entreaspas = entreaspas.replace(",","");
        tratado = preaspas + entreaspas + posaspas;
        System.out.println("beginsub = "+subegin);
        System.out.println("endsub = "+subend);
        System.out.println("preaspas = "+preaspas);
        System.out.println("entreaspas = "+entreaspas);
        System.out.println("tratado = "+tratado);
        System.out.println("Linha 2: "+line);
        return (line);
    }
}
