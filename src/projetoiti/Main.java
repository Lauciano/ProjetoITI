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

public class Main {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Leitor leitor = new Leitor("files/soccer_ball", 45, 1);
        Arvore raiz = new Arvore(0);
        Arvore.geraArvore(raiz, leitor, 2);
        
        Teste t1 = new Teste("files/dollar_bill/image_0001.jpg",45);
        t1.codifica(raiz, 2);
    }
        
        /*System.out.println("Iniciando leitura...");
        Arvore raiz = new Arvore(0);
        Leitor leitor = null;
        Byte b;
        leitor = new Leitor("files/soccer_ball", 45, 1);

        FileOutputStream fop1 = null;
        try {
            fop1 = new FileOutputStream(new File("files/dollar_bill.txt").getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileOutputStream fop2 = null;
        try {
            fop2 = new FileOutputStream(new File("files/stegosaurus.txt").getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileOutputStream fop3 = null;
        try {
            fop3 = new FileOutputStream(new File("files/soccer_ball.txt").getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArithEncoder encoder1 = new ArithEncoder(fop1);
        ArithEncoder encoder2 = new ArithEncoder(fop2);
        ArithEncoder encoder3 = new ArithEncoder(fop3);

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
        //O contexto m�ximo k passado como par�metro
        // Aten��o: ainda n�o est� tratando o mecanismo de exclus�o
        //          mesmo assim funciona para o caso de n�o us�-lo
        // Retorno:
        //		Inteiro		low
        //					high
        //					total
        System.out.println("Gerando código...");
        Arvore.geraArvore(raiz, leitor, 2);

        Leitor leitor2 = new Leitor("files/dollar_bill", 1, 49);
        ArrayList<Codigo> inteiro1 = Arvore.geraCodigoInteiroEstatico(raiz, leitor2, 2);

        Leitor leitor3 = new Leitor("files/stegosaurus", 1, 49);
        ArrayList<Codigo> inteiro2 = Arvore.geraCodigoInteiroEstatico(raiz, leitor3, 2);

        Leitor leitor4 = new Leitor("files/soccer_ball", 1, 49);
        ArrayList<Codigo> inteiro3 = Arvore.geraCodigoInteiroEstatico(raiz, leitor4, 2);
        
        ImageIO.write(leitor2.getPasta()[0].getImage(), "png", new File("resultado2.png"));
        ImageIO.write(leitor3.getPasta()[0].getImage(), "png", new File("resultado3.png"));
        ImageIO.write(leitor4.getPasta()[0].getImage(), "png", new File("resultado4.png"));

        //ArrayList<Codigo> inteiro = Arvore.geraCodigoInteiro(raiz, leitor, 2);

        System.out.println("Código de tamanho " + inteiro1.size());
        System.out.println("Código de tamanho " + inteiro2.size());
        System.out.println("Código de tamanho " + inteiro3.size());

        System.out.println("Codificando...");
        for (Codigo i : inteiro1) {
            try {
                encoder1.encode(i.getLow(), i.getHigh(), i.getTotal());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Codigo i : inteiro2) {
            try {
                encoder2.encode(i.getLow(), i.getHigh(), i.getTotal());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Codigo i : inteiro3) {
            try {
                encoder3.encode(i.getLow(), i.getHigh(), i.getTotal());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Concluído.");
    }*/
}
