package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        System.out.println("Iniciando leitura...");
        Arvore raiz = new Arvore(0);
        Leitor leitor = null;

        try {
            leitor = new Leitor("files/texto.txt"); // Arquivo a ser lido
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(new File("files/saida.txt").getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArithEncoder encoder = new ArithEncoder(fop);

        //Chamada
        //Par�metros
        //O contexto raiz
        //O leitor
        //O contexto m�ximo k passado como par�metro
        // Aten��o: ainda n�o est� tratando o mecanismo de exclus�o
        //          mesmo assim funciona para o caso de n�o us�-lo
        //ArrayList<Intervalo> intv = Contexto.geraCodigo(raiz, leitor, 2);
        //System.out.println("Saida:");
        //for(Intervalo i : intv){
        //	System.out.println(i);
        //}
        //Chamada
        //Par�metros
        //O contexto raiz
        //O leitor
        //O contexto m�ximo k passado como par�metro
        // Aten��o: ainda n�o est� tratando o mecanismo de exclus�o
        //          mesmo assim funciona para o caso de n�o us�-lo
        // Retorno:
        //		Inteiro		low
        //					high
        //					total
        System.out.println("Gerando código...");
        Arvore.geraArvore(raiz, leitor, 2);
        
        Leitor leitor2 = new Leitor("files/texto.txt");
        ArrayList<Codigo> inteiro = Arvore.geraCodigoInteiroEstatico(raiz, leitor2, 2);
        
        //ArrayList<Codigo> inteiro = Arvore.geraCodigoInteiro(raiz, leitor, 2);
        
        System.out.println("C�digo de tamanho " + inteiro.size());

        System.out.println("Codificando...");
        for (Codigo i : inteiro) {
            try {
                encoder.encode(i.getLow(), i.getHigh(), i.getTotal());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Concluído.");
        return;
    }
}
