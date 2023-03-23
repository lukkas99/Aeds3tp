import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CRUD {
    static int ultimoId = 0;

    public static int getUltimoId(){
        return ultimoId;
    }

    // Create
    public static boolean create(RandomAccessFile raf, Artista art) {
        boolean resp = false;

        try {
            raf.seek(raf.length()); // Coloca o ponteiro no final do arquivo
            raf.writeByte(0);    // Coloca uma lapide
            raf.writeInt(art.toByteArray().length);// Escreve o tamanho do registro em bytes

            // Escreve os dados da conta no arquivo
            raf.writeInt(art.getId());
            raf.writeUTF(art.getArtist());
            raf.writeUTF(art.getCountry());
            raf.writeUTF(art.getPeriodActive());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String str = formatter.format(art.getYear());
            raf.writeUTF(str);
            raf.writeInt(art.getNmbrGenre());
            for (int i = 0; i < art.getNmbrGenre(); i++) {
                raf.writeUTF(art.getGenre()[i]);
            }
            raf.writeFloat(art.getTcu());
            raf.writeFloat(art.getSales());

            raf.seek(0); // Coloca o ponteiro no início do arquivo
            raf.writeInt(ultimoId); // Atualiza o ultimo id

            resp = true;
        } catch (Exception e) {
            System.out.println("!Erro ao inserir registro!");
        }

        return resp;
    }
    
    //Lê o arquivo e retorna um obejeto, porem começa a ler da posicao seek(4);
    public static Artista readAll(RandomAccessFile raf, int id) { 
        try {
            Artista art = new Artista();
            raf.seek(4);// Posiciona o ponteiro no inicio do arquivo

            while (raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
                if (raf.readByte() == 0) { // Se a lapide for 0 (ativo)
                    int tam = raf.readInt(); //tamanho do registro
                    //System.out.println("-------------------------------------");
                    //System.out.println("tam = "+tam);
                    art.setId(raf.readInt()); 
                    //System.out.println("id = "+art.getId());
                    if (art.getId() == id) { // Se o id do artista for igual ao id passado
                        art.setArtist(raf.readUTF()); 
                        art.setCountry(raf.readUTF());
                        art.setPeriodActive(raf.readUTF());
                        // Cria um novo objeto Date com o valor lido do arquivo formatado pelo simpledateformat
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = formatter.parse(raf.readUTF()); //le a data do arquivo
                        art.setYear(data);                          //escreve no objeto art
                        //cria um arranjo de strings para controlar campo generos
                        art.setNmbrGenre(raf.readInt());
                        String[] genre = new String[art.getNmbrGenre()]; 
                        for ( int i = 0; i < art.getNmbrGenre(); i++){
                            genre[i] = raf.readUTF();
                        }           
                        art.setGenre(genre); 
                        art.setTcu(raf.readFloat());
                        //System.out.println("tcu = "+art.getTcu());
                        art.setSales(raf.readFloat());
                        //System.out.println("sales = "+art.getSales());

                        return art;
                    } else {
                        //System.out.println("skip = "+(tam - 4));
                        raf.skipBytes(tam - 4); // Pula o resto do registro
                    }
                } else {
                    raf.skipBytes(raf.readInt()); // Pula o registro
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("!Erro ao ler registro!");
            //return null;
        }
        return null;
    }

    //Lê o arquivo e retorna um obejeto, porem começa a ler da posicao seek(0);
    //Para fazer a intercalacao
    public static Artista readAll2(RandomAccessFile raf, int id) { 
        try {
            Artista art = new Artista();
            raf.seek(0);// Posiciona o ponteiro no inicio do arquivo

            while (raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
                if (raf.readByte() == 0) { // Se a lapide for 0 (ativo)
                    int tam = raf.readInt(); //tamanho do registro

                    art.setId(raf.readInt()); 
                    
                    if (art.getId() == id) { // Se o id do artista for igual ao id passado
                        art.setArtist(raf.readUTF()); 
                        art.setCountry(raf.readUTF());
                        art.setPeriodActive(raf.readUTF());
                        // Cria um novo objeto Date com o valor lido do arquivo formatado pelo simpledateformat
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = formatter.parse(raf.readUTF()); //le a data do arquivo
                        art.setYear(data);                          //escreve no objeto art
                        //cria um arranjo de strings para controlar campo generos
                        art.setNmbrGenre(raf.readInt());
                        String[] genre = new String[art.getNmbrGenre()]; 
                        for ( int i = 0; i < art.getNmbrGenre(); i++){
                            genre[i] = raf.readUTF();
                        }           
                        art.setGenre(genre); 
                        art.setTcu(raf.readFloat());
                        art.setSales(raf.readFloat());

                        return art;
                    } else {
                        raf.skipBytes(tam - 4); // Pula o resto do registro
                    }
                } else {
                    raf.skipBytes(raf.readInt()); // Pula o registro
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("!Erro ao ler registro!");
            //return null;
        }
        return null;
    }

    // Pesquisa uma conta pelo id 
    public static Artista readById(RandomAccessFile raf, int search) { 
        try {
            Artista art = new Artista();

            raf.seek(4);// Posiciona o ponteiro no inicio do arquivo

            while (raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
                if (raf.readByte() == 0) { // Se a lapide for 0 (ativo)
                    int tam = raf.readInt(); //tamanho do registro

                    art.setId(raf.readInt()); 
                    if (art.getId() == search) { // Se o id do artista for igual ao id pesquisado
                        art.setArtist(raf.readUTF()); 
                        art.setCountry(raf.readUTF());
                        art.setPeriodActive(raf.readUTF());
                        // Cria um novo objeto Date com o valor lido do arquivo formatado pelo simpledateformat
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = formatter.parse(raf.readUTF()); //le a data do arquivo
                        art.setYear(data);                          //escreve no objeto art
                        //cria um arranjo de strings para controlar campo generos
                        art.setNmbrGenre(raf.readInt());
                        String[] genre = new String[art.getNmbrGenre()]; 
                        for ( int i = 0; i < art.getNmbrGenre(); i++){
                            genre[i] = raf.readUTF();
                        }           
                        art.setGenre(genre); 
                        art.setTcu(raf.readFloat());
                        art.setSales(raf.readFloat());

                        return art;
                    } else {
                        raf.skipBytes(tam - 4); // Pula o resto do registro
                    }
                } else {
                    raf.skipBytes(raf.readInt()); // Pula o registro
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println("!Erro ao ler registro!");
            return null;
        }
    }

    // Atualiza um artista
    public static boolean update(RandomAccessFile raf, Artista art) { 
        try {
            int a = 0;
            raf.seek(0);
            a = raf.readInt();
            a = a + 1;
            //System.out.println("A = "+a);
            //raf.seek(4); // Posiciona o ponteiro no inicio do arquivo
            while (raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
                if (raf.readByte() == 0) { // Se a lapide for 0 (ativo)
                    int tam = raf.readInt();

                    if (raf.readInt() == art.getId()) { // Se o id do artista for igual ao id da conta a ser atualizada
                        if (tam >= art.toByteArray().length) { // Se o tamanho do registro for maior ou igual ao tamanho do registro atualizado encaixar no mesmo registro
                            //System.out.println("tam ="+tam+"_art.toByte = "+art.toByteArray().length);
                            raf.writeUTF(art.getArtist());
                            raf.writeUTF(art.getCountry());
                            raf.writeUTF(art.getPeriodActive());
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							String str = formatter.format(art.getYear());
                            raf.writeUTF(str);
                            raf.writeInt(art.getNmbrGenre());
                            for (int i = 0; i < art.getNmbrGenre(); i++) {
                                raf.writeUTF(art.getGenre()[i]);
                            }
                            raf.writeFloat(art.getTcu());
                            raf.writeFloat(art.getSales());

                            return true;
                        } else { // Se o tamanho do registro for menor que o tamanho do registro atualizado sera necessario criar um novo registro e excluir o antigo
                            art.setId(a);//novo id
                            raf.seek(raf.getFilePointer() - 9); // Volta o ponteiro para o inicio do registro
                            raf.writeByte(1); // Escreve a lapide 1 (excluido)
                            raf.seek(0);//volta ao começo do arquivo
                            raf.write(a);//atualiza ultimo id
                            return create(raf, art); // Cria um novo registro
                        }
                    } else {
                        raf.skipBytes(tam - 4); // Pula o resto do registro
                    }
                } else {
                    raf.skipBytes(raf.readInt()); // Pula o registro
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("!Erro ao atualizar registro!");
            return false;
        }
    }

    public static boolean delete(RandomAccessFile raf, Artista art) { // Exclui uma conta
        try {
            raf.seek(4); // Posiciona o ponteiro no inicio do arquivo
            while(raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
                if(raf.readByte() == 0) { // Se a lapide for 0 (ativo)
                    int tam = raf.readInt();
                    int id = raf.readInt();

                    if(id == art.getId()) { // Se o id do artista for igual ao id do registro a ser excluido
                        raf.seek(raf.getFilePointer() - 9); // Volta o ponteiro para o inicio do registro
                        raf.writeByte(1); // Escreve a lapide 1 (excluido)
                        return true;
                    }else {
                        raf.skipBytes(tam - 4); // Pula o resto do registro
                    }
                }else {
                    raf.skipBytes(raf.readInt()); // Pula o registro
                }
            }
            return false;
        }catch (Exception e) {
            System.out.println("!Erro ao deletar registro!");
            return false;
        }
    }
}