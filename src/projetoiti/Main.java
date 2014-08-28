package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        String[] categoria = {"dollar_bill", "soccer_ball", "stegosaurus"};
        Arvore[] raiz = new Arvore[3];
        Leitor[] leitor = new Leitor[3];
        
        for(int i = 0; i < 3; i++){
            raiz[i] = new Arvore(0);
            leitor[i] = new Leitor("files/" + categoria[i], 45, 1);
        }

        System.out.println("Gerando código...");
        for(int i = 0; i < 3; i++){
            Arvore.geraArvore(raiz[i], leitor[i], 3);
        }

        System.out.println("Codificando...");
        Teste[] dollar_bill = new Teste[5];
        Teste[] soccer_ball = new Teste[5];
        Teste[] stegosaurus = new Teste[5];
        
        for(int j = 0; j < 5; j++) {
            for(int i = 0; i < 3; i++){
                dollar_bill[j] = new Teste("dollar_bill", categoria[i], j + 46);
                dollar_bill[j].codifica(raiz[i], 3);
                System.out.println("dollar_bill00" + (j + 46) + " com arvore de " + categoria[i] + ": " + dollar_bill[j].getTaxa());
            }
            for(int i = 0; i < 3; i++){
                soccer_ball[j] = new Teste("soccer_ball", categoria[i], j + 46);
                soccer_ball[j].codifica(raiz[i], 3);
                System.out.println("soccer_ball00" + (j + 46) + " com arvore de " + categoria[i] + ": " + soccer_ball[j].getTaxa());
            }
            for(int i = 0; i < 3; i++){
                stegosaurus[j] = new Teste("stegosaurus", categoria[i], j + 46);
                stegosaurus[j].codifica(raiz[i], 3);
                System.out.println("stegosaurus00" + (j + 46) + " com arvore de " + categoria[i] + ": " + stegosaurus[j].getTaxa());
            }
        }
        
        System.out.println("Concluído.");
    }
}
