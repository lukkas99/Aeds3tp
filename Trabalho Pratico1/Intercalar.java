import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Intercalar extends CRUD {

    // Deleta os arquivos já existentes
    public static void deleteFiles() { 
    File file1 = new File("arq1.bin");
    File file2 = new File("arq2.bin");
    File file3 = new File("arq3.bin");
    File file4 = new File("arq4.bin");
    File file5 = new File("arqFinal.bin");

    file1.delete();
    file2.delete();
    file3.delete();
    file4.delete();
    file5.delete();
    }

    //Intercalaçao comun balanceada
    public static boolean comunSort(RandomAccessFile raf)throws IOException{
        
        // Deleta os arquivos já existentes
        deleteFiles();

        System.out.println("\n-> Intercalando ...");

        // Cria um novo array list artistas
        ArrayList<Artista> artis = new ArrayList<Artista>();
        // Cria um objeto artista
        Artista art = new Artista();

        //Cria os arquivos .bin arq1 e arq2
        RandomAccessFile arq1 = new RandomAccessFile("arq1.bin", "rw");
        RandomAccessFile arq2 = new RandomAccessFile("arq2.bin", "rw");

        // Posiciona o ponteiro no inicio do arquivo
        raf.seek(4); 
        
        // Enquanto o ponteiro nao chegar no final do arquivo
        while(raf.getFilePointer() < raf.length()){
            //Se lapide == 0 (ativo)
            if (raf.readByte() == 0) {
                //Le o tamanho
                raf.readInt();
                //retorna objeto art com o ID lido 
                art = CRUD.readById(raf, raf.readInt());
                //adiciona art a lista de artistas
                artis.add(art);
            } else {
                //pula o registro
                raf.skipBytes(raf.readInt());
            }
        }

        // Cria um array temporario para armazenar os artistas
        ArrayList<Artista> artisTmp = new ArrayList<Artista>(); 
        int contador = 0; // Contador para saber quantos artistas foram adicionadas no arquivo
        System.out.println("artis.size() ="+artis.size());
        while (artis.size() > 0) {  // Enquanto o array artis nao estiver vazio
            for (int j = 0; j < 5; j++) { // Adiciona 5 artistas no array temporário
                if (artis.size() > 0) { // Se o array artis nao estiver vazio
                    artisTmp.add(artis.get(0)); // Adiciona primeiro artista do array artis no array temporário
                    artis.remove(0); // Remove primeiro artista do array artis
                }
            }

            // Ordena o array temporário
            artisTmp.sort((Artista a1, Artista a2) -> a1.getId() - a2.getId());

            contador++;

            // Se o contador for impar adiciona no arquivo 1
            if (contador % 2 != 0) { 
                System.out.println("entrou 1");
                for (Artista a : artisTmp) {
                    System.out.println("entrou 1,5 = "+contador);
                    arq1.writeByte(0);
                    arq1.writeInt(a.toByteArray().length);
                    arq1.write(a.toByteArray());
                }
            // Se o contador for par adiciona no arquivo 2
            } else { 
                System.out.println("entrou 2");
                for (Artista a : artisTmp) {
                    System.out.println("entrou 2,5 = "+contador);
                    arq2.writeByte(0);
                    arq2.writeInt(a.toByteArray().length);
                    arq2.write(a.toByteArray());
                }
            }

            artisTmp.clear(); 
        }

      

        ArrayList<Artista> artAux1 = new ArrayList<Artista>();
        ArrayList<Artista> artAux2 = new ArrayList<Artista>();

        System.out.println("\n-> Intercalacao 1 ...");

        // Posiciona o ponteiro no inicio do arquivo 1
        arq1.seek(0); 
        while (arq1.getFilePointer() < arq1.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
            arq1.readByte();
            arq1.readInt();
            art = readAll2(arq1, arq1.readInt()); // Le o registro
            artAux1.add(art); 
        }
        // Posiciona o ponteiro no inicio do arquivo 2
        arq2.seek(0); 
        while (arq2.getFilePointer() < arq2.length()) { 
            arq2.readByte();
            arq2.readInt();
            art = readAll2(arq2, arq2.readInt()); 
            artAux2.add(art);
        }

        RandomAccessFile arq3 = new RandomAccessFile("arq3.bin", "rw");
        RandomAccessFile arq4 = new RandomAccessFile("arq4.bin", "rw");

        // Contador para saber quantos artistas foram adicionados no arquivo
        contador = 0; 
        artisTmp.clear(); // Limpa o array temporário
        int m = 5; // Tamanho do array temporário

        // System.out.println("artAux1.size() ="+artAux1.size());
        // System.out.println("artAux2.size() ="+artAux2.size());

        // Enquanto o array artAux1 ou o array artAux2 nao estiverem vazios
        while (artAux1.size() > 0 || artAux2.size() > 0) { 
                                                           
            for (int i = 0; i < m; i++) {
                if (artAux1.size() > 0) { 
                                          
                    artisTmp.add(artAux1.get(0));
                    artAux1.remove(0);
                }
                if (artAux2.size() > 0) { 
                                          
                    artisTmp.add(artAux2.get(0));
                    artAux2.remove(0);
                }
            }

            // Ordena o array temporário
            artisTmp.sort((Artista a1, Artista a2) -> a1.getId() - a2.getId()); 

            contador++;

            // Se o contador for impar adiciona no arquivo 3
            if (contador % 2 != 0) { 
                for (Artista a : artisTmp) {
                    arq3.writeByte(0);
                    arq3.writeInt(a.toByteArray().length);
                    arq3.write(a.toByteArray());
                }
            } else { // Se o contador for par adiciona no arquivo 4
                for (Artista a : artisTmp) {
                    arq4.writeByte(0);
                    arq4.writeInt(a.toByteArray().length);
                    arq4.write(a.toByteArray());
                }
            }

            artisTmp.clear(); // Limpa o array temporário
        }

        int qdt = 2; // Numero inicial da intercalação
        while (arq2.length() > 0) { // Enquanto o arquivo 2 nao estiver vazio

            System.out.println("\n-> Intercalacao " + qdt + " ..."); // Imprime o numero da intercalação
            // Enquanto o ponteiro nao chegar no final do arquivo 3 le o
            // registro e adiciona no array artAux1
            arq3.seek(0);
            while (arq3.getFilePointer() < arq3.length()) { 
                                                            
                arq3.readByte();
                arq3.readInt();
                art = readAll2(arq3, arq3.readInt());
                artAux1.add(art);
            }

            // Enquanto o ponteiro nao chegar no final do arquivo 4 le o
            // registro e adiciona no array artAux2
            arq4.seek(0);
            while (arq4.getFilePointer() < arq4.length()) { 
                                                            
                arq4.readByte();
                arq4.readInt();
                art = readAll2(arq4, arq4.readInt());
                artAux2.add(art);
            }

            arq1.setLength(0); // Limpa o arquivo1
            arq2.setLength(0); // Limpa o arquivo2

            contador = 0;
            artisTmp.clear();// Limpa o array temporário
            m *= 2; // Aumenta o tamanho do array temporário
            // System.out.println("artAux1.size() ="+artAux1.size());
            // System.out.println("artAux2.size() ="+artAux2.size());
            // Enquanto o array artAux1 ou o array artAux2 nao estiverem vazios
            // Adiciona os registros nos arrays temporários
            while (artAux1.size() > 0 || artAux2.size() > 0) { 

                for (int i = 0; i < m; i++) { 
                    // Se o array artAux1 nao estiver vazio adiciona o 
                    // primeiro artista no array temporário e remove do array artAux1
                    if (artAux1.size() > 0) { 
                                              
                        artisTmp.add(artAux1.get(0));
                        artAux1.remove(0);
                    }
                    // Se o array artAux2 nao estiver vazio adiciona o 
                    // primeiro registro no array temporário e remove do array artAux1
                    if (artAux2.size() > 0) { 
                                              
                        artisTmp.add(artAux2.get(0));
                        artAux2.remove(0);
                    }
                }

                artisTmp.sort((Artista a1, Artista a2) -> a1.getId() - a2.getId()); 

                contador++;

                if (contador % 2 != 0) { // Se o contador for impar adiciona no arquivo 1
                    for (Artista a : artisTmp) {
                        arq1.writeByte(0);
                        arq1.writeInt(a.toByteArray().length);
                        arq1.write(a.toByteArray());
                    }
                } else { // Se o contador for par adiciona no arquivo 2
                    for (Artista a : artisTmp) {
                        arq2.writeByte(0);
                        arq2.writeInt(a.toByteArray().length);
                        arq2.write(a.toByteArray());
                    }
                }

                artisTmp.clear(); // Limpa o array temporário
            }
            qdt++;
        }

        // ------------------------------------------------------------------- //
        
        RandomAccessFile arqFinal = new RandomAccessFile("arqFinal.bin", "rw");
        arqFinal.seek(0); 
        arqFinal.writeInt(CRUD.getUltimoId()); // Grava o último id

        arqFinal.seek(4);  // Pula o último id
        arq1.seek(0); // Volta para o início do arquivo1

        while (arq1.getFilePointer() < arq1.length()) { // Copia o arquivo 1 para o arquivo final
            arq1.readByte(); // Lê o byte de lapide
            arq1.readInt(); // Lê o tamanho do registro
            art = readAll2(arq1, arq1.readInt()); // Lê o registro
            arqFinal.writeByte(0); // Escreve o byte de lapide
            arqFinal.writeInt(art.toByteArray().length); // Escreve o tamanho do registro
            arqFinal.write(art.toByteArray()); // Escreve o registro
        }

        // Fecha os arquivos
        arq1.close(); 
        arq2.close();
        arq3.close();
        arq4.close();
        arqFinal.close();

        return true;
    }

    //Intercalação balanceada com blocos de tamanho variavel
    public static boolean variableSizeSort(RandomAccessFile raf) throws IOException {
        
        // Cria dois novos array list artistas
        ArrayList<Artista> artis = new ArrayList<Artista>();
        ArrayList<Artista> artisTmp = new ArrayList<Artista>();
      
        //Novo objeto artista
        Artista art = new Artista();

        // Cria dois novos arquivos temporarios para intercalacao
        RandomAccessFile arqV1 = new RandomAccessFile("arqV1.bin", "rw");
        RandomAccessFile arqV2 = new RandomAccessFile("arqV2.bin", "rw");

        int contador = 0; // Contador para saber quantos artistas foram adicionadas no arquivo
        int m = 5; // Tamanho do array temporário

        while (artis.size() > 0) {  // Enquanto o array artis nao estiver vazio
            for (int i = 0; i < m; i++) { // Adiciona m artistas no array temporário
                if (artis.size() > 0) {   // Se o array artis nao estiver vazio
                    artisTmp.add(artis.get(0)); // Adiciona primeiro artista do array artis no array temporário
                    artis.remove(0); // Remove primeiro artista do array artis
                }
            }
            // Ordena o array temporário
            artisTmp.sort((Artista a1, Artista a2) -> a1.getId() - a2.getId()); 

            contador++;

            // Se o contador for impar adiciona no arquivo v1
            if (contador % 2 != 0) { 
                for (Artista a : artisTmp) {
                    arqV1.writeByte(0);
                    arqV1.writeInt(a.toByteArray().length);
                    arqV1.write(a.toByteArray());
                }
            } else { // Se o contador for par adiciona no arquivo v2
                for (Artista a : artisTmp) {
                    arqV2.writeByte(0);
                    arqV2.writeInt(a.toByteArray().length);
                    arqV2.write(a.toByteArray());
                }
            }

            artisTmp.clear(); 
        }

        System.out.println("\n-> Intercalacao 2 ...");
        
        // Cria dois novos array list temporarios
        ArrayList<Artista> artAux3 = new ArrayList<Artista>();
        ArrayList<Artista> artAux4 = new ArrayList<Artista>();
        arqV1.seek(0); 
        // Enquanto o ponteiro nao chegar no final do arquivo v1 le o
        // registro e adiciona no array artAux3
        while (arqV1.getFilePointer() < arqV1.length()) { 
            arqV1.readByte();
            arqV1.readInt();
            art = readAll2(arqV1, arqV1.readInt()); 
           artAux3.add(art); 
        }

        arqV2.seek(0);
        // Enquanto o ponteiro nao chegar no final do arquivo v2 le o
        // registro e adiciona no array artAux4 
        while (arqV2.getFilePointer() < arqV2.length()) { 
            arqV2.readByte();
            arqV2.readInt();
            art = readAll2(arqV2, arqV2.readInt()); 
            artAux4.add(art);
        }

        RandomAccessFile arqV3 = new RandomAccessFile("arqV3.bin", "rw");
        RandomAccessFile arqFinal = new RandomAccessFile("arqFinalV.bin", "rw");

        arqFinal.seek(0); 
        arqFinal.writeInt(CRUD.getUltimoId()); // Grava o último id

        arqFinal.seek(4); // Pula o último id
        arqV1.seek(0);   // Volta para o início do arquivo1
        while (arqV1.getFilePointer() < arqV1.length()) { // Copia o arquivo v1 para o arquivo final
            arqV1.readByte(); // Lê o byte de lapide
            arqV1.readInt();  // Lê o tamanho do registro
            art = readAll2(arqV1, arqV1.readInt()); // Lê o registro
            arqFinal.writeByte(0); // Escreve o byte de lapide
            arqFinal.writeInt(art.toByteArray().length); // Escreve o tamanho do registro
            arqFinal.write(art.toByteArray()); // Escreve o registro
        }

        // Fecha os arquivos
        arqV1.close(); 
        arqV2.close();
        arqV3.close();
        arqFinal.close();

        return true;
    }

    // //Intercalação com selecao por substituicao
    public static boolean substitutionSort(List<String[]> dados, String arquivoSaida) {
        int tamBloco = 4;
        int numBlocos = (int) Math.ceil((double) dados.size() / tamBloco);

        try {
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(arquivoSaida));

            for (int bloco = 0; bloco < numBlocos; bloco++) {
                int inicioBloco = bloco * tamBloco;
                int fimBloco = Math.min((bloco + 1) * tamBloco, dados.size());

                PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

                for (int i = inicioBloco; i < fimBloco; i++) {
                    String[] linha = dados.get(i);
                    String[] colunas = linha[0].split(";");

                    int id = Integer.parseInt(colunas[0]);
                    pq.add(id);
                }

                ArrayList<String[]> linhasOrdenadas = new ArrayList<String[]>();
                while (!pq.isEmpty()) {
                    int id = pq.poll();

                    for (int i = inicioBloco; i < fimBloco; i++) {
                        String[] linha = dados.get(i);
                        String[] colunas = linha[0].split(";");

                        if (Integer.parseInt(colunas[0]) == id) {
                            linhasOrdenadas.add(linha);
                            break;
                        }
                    }
                }

                for (int i = 0; i < linhasOrdenadas.size(); i++) {
                    String[] linha = linhasOrdenadas.get(i);
                    csvWriter.write(linha[0] + "," + linha[1] + "\n");
                }
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
