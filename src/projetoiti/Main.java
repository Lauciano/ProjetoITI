package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

        //Chamada
        //Parâmetros
        //O contexto raiz
        //O leitor
        //O contexto máximo k passado como par�metro
        // Atenção: ainda não está tratando o mecanismo de exclusão
        //          mesmo assim funciona para o caso de não usá-lo
        //ArrayList<Intervalo> intv = Contexto.geraCodigo(raiz, leitor, 2);
        //System.out.println("Saida:");
        //for(Intervalo i : intv){
        //	System.out.println(i);
        //}
        //Chamada
        //Par�metros
        //O contexto raiz
        //O leitor
        //O contexto máximo k passado como par�metro
        // Atenção: ainda não está tratando o mecanismo de exclus�o
        //          mesmo assim funciona para o caso de n�o us�-lo
        // Retorno:
        //		Inteiro		low
        //					high
        //					total

public class Main {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        System.out.println("Iniciando leitura...");
        Byte b;
        String[] categoria = {"accordion", "airplanes", "brain", "buddha", "butterfly",
                              "camera", "chair", "chandelier", "cougar_face", "crocodile_head",
                              "cup", "dalmatian", "dollar_bill", "dolphin", "dragonfly",
                              "electric_guitar", "elephant", "euphonium", "ewer", "faces_easy",
                              "ferry", "flamingo", "gramophone", "grand_piano", "hawksbill",
                              "hedgehog", "ketch", "lamp", "leopards", "lotus",
                              "menorah", "minaret", "motorbikes", "nautilus", "pizza",
                              "pyramid", "revolver", "schooner", "sea_horse", "soccer_ball",
                              "starfish", "stegosaurus", "stop_sign", "sunflower", "trilobite",
                              "umbrella", "watch", "wheelchair", "windsor_chair", "yin_yang"};
        Arvore raiz;
        /*Leitor leitor;
        OutputStream os;
        OutputStream buffer;
        ObjectOutput output;
        
        for(int i = 5; i < 6; i++){
            raiz = new Arvore(0);
            leitor = new Leitor("files/" + categoria[i], 45, 1);
            System.out.println("Gerando código de " + categoria[i]);
            Arvore.geraArvore(raiz, leitor, 3);
            os = new FileOutputStream("files/arvore/" + categoria[i]);
            buffer = new BufferedOutputStream(os);
            output = new ObjectOutputStream(buffer);
            output.writeObject(raiz);
            output.close();
        }

        try {
            Arvore a = (Arvore) input.readObject();
            System.out.println(a.getTotal());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        InputStream file;
        InputStream buffer2;
        ObjectInput input;
        Teste[][] teste = new Teste[50][5];
        PrintWriter pw = new PrintWriter("files/relatorio25a49.txt");

        for(int j = 25; j < 50; j++) { // Pasta
            System.out.println("Codificando " + categoria[j]);
            for(int k = 0; k < 5; k++) { // Imagem
                for(int i = 0; i < 50; i++){ // Arvore
                    file = new FileInputStream("files/arvore/" + categoria[i]);
                    buffer2 = new BufferedInputStream(file);
                    input = new ObjectInputStream(buffer2);
                    try {
                        raiz = (Arvore) input.readObject();
                        teste[j][k] = new Teste(categoria[j], categoria[i], k + 46);
                        teste[j][k].codifica(raiz, 3);
                        pw.println(categoria[j] + "00" + (k + 46) + " com arvore de " + categoria[i] + ": " + teste[j][k].getTaxa());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    input.close();
                    buffer2.close();
                    file.close();
                }
            }
        }
        
        System.out.println("Concluído.");
    }
}
