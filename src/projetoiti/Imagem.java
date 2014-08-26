/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Thiago
 */
public class Imagem {
    
    private BufferedImage image;
    private BufferedImage imageGray;
    private double[][] yMatrix;
    
    public Imagem(String end){
        
        try {
            image = ImageIO.read(new File(end));
        } catch (IOException ex) {
            System.err.println("Problemas na leitura da imagem! Verifique o caminho.");
        }
    }
    
    public void toYIQ(){
           
        double[] dArray = new double[4];
        double[] yiqArray = new double[4];
        
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                
                image.getRaster().getPixel(i, j, dArray);
                
                yiqArray[0] = 0.299*dArray[0] + 0.587*dArray[1] + 0.114*dArray[2];
                //yiqArray[1] = 0.596*dArray[0] - 0.275*dArray[1] - 0.321*dArray[2];
                //yiqArray[2] = 0.212*dArray[0] - 0.523*dArray[1] + 0.311*dArray[2];
                   
                yMatrix[i][j] = yiqArray[0];
                
            }
        }
    }
    
    public double[][] getYMatrix(){
        toYIQ();
        return yMatrix;
    }
    
    /*private BufferedImage toYIQ_TESTE(){
        
        double[] dArray = new double[4];
        double[] dArrayTemp = new double[4];
        double[] yiqArray = new double[4];
        
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                
                image.getRaster().getPixel(i, j, dArray);
                
                yiqArray[0] = 0.299*dArray[0] + 0.587*dArray[1] + 0.114*dArray[2];
                yiqArray[1] = 0.596*dArray[0] - 0.275*dArray[1] - 0.321*dArray[2];
                yiqArray[2] = 0.212*dArray[0] - 0.523*dArray[1] + 0.311*dArray[2];
                   
                
                dArrayTemp[0] = yiqArray[0] + 0.956*yiqArray[1] + 0.621*yiqArray[2];
                dArrayTemp[1] = yiqArray[0] - 0.272*yiqArray[1] - 0.647*yiqArray[2];
                dArrayTemp[2] = yiqArray[0] - 1.105*yiqArray[1] + 1.702*yiqArray[2];
                
                imageGray.getRaster().setPixel(i, j, dArrayTemp);
                
            }
        }
        
        return imageGray;
        
    }*/
    
    public static void main(String[] args) throws IOException{
        Imagem i = new Imagem("periquito.jpg");
    }
    
}
