package projetoiti;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Leitor {

    private DataInputStream data;
    private byte[] simbolo;
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

    public ArrayList<Byte> getAlfabeto() throws FileNotFoundException, IOException {

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

    public Byte getNextByte() throws IOException {

        if (index >= bitfinal) {
            if ((bitfinal = data.read(simbolo)) > -1) {

    			//System.out.println("Li um novo array: ");
                //for(int i = 0; i < bitfinal; i++){
                //	System.out.println((char) simbolo[i] + " - " + (int) simbolo[i]);
                //}
                index = 0;
            } else {
                data.close();
                return null;
            }
        }

        return simbolo[index++];

        /*
         if (proximo) {
         if ((bitfinal = data.read(simbolo)) > -1) {
         index = -1;
         proximo = false;
         } else {
         data.close();
         return null;
         }
         }
        
         if (index < size_simbolo-2) {
         if (bitfinal != size_simbolo) {
         if (bitfinal_aux < bitfinal-1) {
         bitfinal_aux++;
         return simbolo[bitfinal_aux];
         } else {
         data.close();
         return null;
         }
         }
         index++;
         //System.out.println((char) simbolo[index]);
         return simbolo[index];
            
         }
         index++;
         proximo = true;
         //System.out.println((char) simbolo[index]);
        
         return simbolo[index];
         */
    }
}
