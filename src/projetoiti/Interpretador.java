/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Thiago
 */
public class Interpretador {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("files/relatorio0a24.txt"));
        String currentLine;
        double max;
        int maxIndex;
        StringTokenizer tok;
        String nome = null;
        int acertos = 0;
        
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
        
        for(int i = 0; i < 24; i++){
            acertos = 0;
            for(int j = 0; j < 5; j++){
                max = 0.0;
                maxIndex = 0;
                for(int k = 0; k < 50; k++){
                    
                    currentLine = br.readLine();
                    tok = new StringTokenizer(currentLine);
                    
                    nome = tok.nextToken();
                    tok.nextToken();
                    tok.nextToken();
                    tok.nextToken();
                    tok.nextToken();
                    double valor = Double.parseDouble(tok.nextToken());
                    
                    if(valor > max){
                        max = valor;
                        maxIndex = k;
                    }
                    
                }
                
                if(i == maxIndex) acertos++;
                
                System.out.println(nome + " classificado como " + categoria[maxIndex]);
                
            }
           
            System.out.println("Acertos: " + acertos + " Erros: " + (5-acertos));
            
        }
    }
    
}
