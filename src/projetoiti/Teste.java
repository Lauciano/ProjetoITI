/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago
 */
public class Teste {
    
    FileOutputStream fop;
    ArithEncoder encoder;
    Leitor l;
    String pasta;
    int comeco;
    String arvore;
    
    public Teste(String pasta, String arvore, int comeco){
        this.pasta = pasta;
        this.arvore = arvore;
        this.comeco = comeco;
        try {
            if(comeco < 10){
                fop = new FileOutputStream("files/saida/" + pasta + "/image_000" + comeco + "com" + arvore + ".txt");
            } else {
                
            fop = new FileOutputStream("files/saida/" + pasta + "/image_00" + comeco + "com" + arvore + ".txt");
            }
            l = new Leitor("files/" + pasta, 1, comeco);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
        encoder = new ArithEncoder(fop);
        
    }
    
    public void codifica(Arvore a, int contextoMaximo) {
        try {
            ArrayList<Codigo> codigo = Arvore.geraCodigoInteiroEstatico(a, l, contextoMaximo);
            for (Codigo i : codigo) {
                encoder.encode(i.getLow(), i.getHigh(), i.getTotal());
            }
        } catch (IOException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getTaxa(){
        File comprimido = new File("files/saida/" + pasta + "/image_00" + comeco + "com" + arvore + ".txt");
        
        return l.getContador()/comprimido.length();
    }

}
