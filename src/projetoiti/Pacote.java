/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import com.colloquial.arithcode.ArithEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago
 */
public class Pacote {
    
    private String classe;
    private FileOutputStream fop;
    ArithEncoder[] encodersTeste;
    Leitor[] leitores;
    
    public Pacote(String classe, int nTreino, int nTeste){
        this.classe = classe;
        try {
            fop = new FileOutputStream("files/" + classe + "_result");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pacote.class.getName()).log(Level.SEVERE, null, ex);
        }
        encodersTeste = new ArithEncoder[nTeste];
        leitores = new Leitor[nTeste];
    }
    
    
    
}
