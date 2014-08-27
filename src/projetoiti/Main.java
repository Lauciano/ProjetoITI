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
        Arvore raiz = new Arvore(0);
        Leitor leitor = null;
        Byte b;
        leitor = new Leitor("files/soccer_ball", 45, 1);

        System.out.println("Gerando código...");
        Arvore.geraArvore(raiz, leitor, 2);

        System.out.println("Codificando...");
        Teste[] dollar_bill = new Teste[5];
        Teste[] soccer_ball = new Teste[5];
        Teste[] stegosaurus = new Teste[5];
        
        for(Teste t : dollar_bill){
            
        }
        

        System.out.println("Concluído.");
    }
}
