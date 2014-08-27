package projetoiti;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Leitor {

    private DataInputStream data;
    private Imagem[] pasta;
    private byte[] simbolo;
    private int pastaindex;
    private int linha;
    private int coluna;
    private int bitfinal;
    private int index;
    private static final int size_simbolo = 1000;
    private String nomeArq;

    public Leitor(String nomeArq) throws FileNotFoundException, IOException {

        this.nomeArq = nomeArq;
        data = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArq)));
        simbolo = new byte[size_simbolo];
        bitfinal = 0;
        index = 0;

    }

    public Leitor(String diretorio, int quantidade, int comeco) throws FileNotFoundException, IOException  {
        StringBuilder sb;
        pasta = new Imagem[quantidade];
        for(int i = comeco, j = 0; i <= quantidade + comeco - 1; i++, j++){
            sb = new StringBuilder(diretorio + "/image_00");
            if(i < 10) {
                sb.append("0").append(i);
            } else {
                sb.append(i);
            }
            sb.append(".jpg");
            pasta[j] = new Imagem(sb.toString());
        }
        simbolo = new byte[size_simbolo];
        pastaindex = 0;
        linha = 0;
        coluna = 0;
    }
    
    public ArrayList<Byte> getAlfabeto() {
        ArrayList<Byte> alfabeto = new ArrayList<Byte>();
        for(Byte i = -128; i < 127; i++){
            alfabeto.add(i);
        }
        alfabeto.add(Byte.MAX_VALUE);
        return alfabeto;
    }
    
    public ArrayList<Byte> getAlfabetoFile() throws FileNotFoundException, IOException {

        int i, j;
        byte[] simbolo_aux = new byte[1000];
        ArrayList<Byte> alfabeto = new ArrayList<Byte>();

        DataInputStream data_aux = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArq)));

        while ((i = data_aux.read(simbolo_aux)) > -1) {
            for (j = 0; j < i; j++) {
                if (!alfabeto.contains(simbolo_aux[j])) {
                    alfabeto.add(simbolo_aux[j]);
                    //System.out.println("Adicionado " + (char) (simbolo_aux[j]));
                }

            }
        }

        Collections.sort(alfabeto);

        data_aux.close();

        return alfabeto;

    }

    public Byte getNextByteInFile() throws IOException {

        if (index >= bitfinal) {
            if ((bitfinal = data.read(simbolo)) > -1) {
                index = 0;
            } else {
                data.close();
                return null;
            }
        }

        return simbolo[index++];
    }
    
    public Byte getNextByte() {
        if(coluna >= pasta[pastaindex].getWidth()){
            linha++;
            if(linha >= pasta[pastaindex].getHeight()){
                pastaindex++;
                System.out.println("linha: " + linha + " coluna: " + coluna);
                if(pastaindex >= pasta.length){
                    return null;
                } else {
                    linha = 0;
                    coluna = 0;
                }
            } else {
                coluna = 0;
            }
        }
        
        return pasta[pastaindex].getYMatrix()[coluna++][linha];
    }
}
