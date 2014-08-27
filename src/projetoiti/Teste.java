/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
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
    
    public Teste(String arquivo,int comeco){
        try {
            fop = new FileOutputStream("saida/" + arquivo);
            l = new Leitor(arquivo,1,comeco);
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

}
