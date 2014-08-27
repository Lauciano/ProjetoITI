/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetoiti;

import de.lmu.ifi.dbs.jfeaturelib.features.Sift;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Thiago
 */
public class Imagem {
    
    private BufferedImage image;
    private BufferedImage copy;
    private Byte[][] yMatrix;
    
    public Imagem(String end){
        //Abertura
        try {
            image = ImageIO.read(new File(end));
        } catch (IOException ex) {
            System.err.println("Problemas na leitura da imagem! Verifique o caminho.");
        }
        
        copy = copyImage(image);
        
        //SIFT
        String OS = System.getProperty("os.name").toLowerCase();
        int width, height;
        boolean arrows = false; //IMPORTANTE
        try {
            width = image.getWidth();
            height = image.getHeight();
            ImageProcessor ip = new ColorProcessor(image);
            ip = ip.convertToByte(false);
            File siftFile = null;
            if (OS.contains("win")) {
                siftFile = new File("siftWin32.exe");
            } else if (OS.contains("unix") || OS.contains("linux")) {
                siftFile = new File("sift");
            } else {
                System.out.println("Operational System not detected");
                return;
            }
            Sift sift = new Sift(siftFile);
            sift.run(ip);

            //note that result.get(i)[0..3] represent y/x/scale/rotation, respectively.
            List<double[]> result = sift.getFeatures();
            for (double[] r : result) {
				//System.out.println("xy    = " + r[1] + "," + r[0]);
                //System.out.println("scale = " + r[2]);
                //System.out.println("rotat = " + r[3]+"\n");

                if (arrows) {
                    drawArrows(image, r[1], r[0], r[2], r[3]);
                } else {
                    drawRectangles(image, r[1], r[0], r[2], r[3]);
                }
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        image = getResult();
        
        //Cinzação
        yMatrix = new Byte[image.getWidth()][image.getHeight()];
        toYIQ();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getCopy() {
        return copy;
    }

    public void setCopy(BufferedImage copy) {
        this.copy = copy;
    }
    
    public BufferedImage getResult(){
        
        double[] ar = new double[3];
        BufferedImage branca = getWhiteImage(image.getWidth(),image.getHeight());
        
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                image.getRaster().getPixel(i, j, ar);
                if(ar[0] == 255.0 && ar[1] == 255.0 && ar[2] == 255.0){
                    copy.getRaster().getPixel(i, j, ar);
                    double[] originArray = {ar[0],ar[1],ar[2]};
                    branca.getRaster().setPixel(i, j, originArray);
                }
            }
        }
        
        return branca;
    }
    
    public BufferedImage copyImage(BufferedImage image){
        
        double arTemp[] = new double[3];
        BufferedImage copy = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < image.getWidth(); i++)
            for(int j = 0; j < image.getHeight(); j++){
                image.getRaster().getPixel(i, j, arTemp);
                double newArray[] = {arTemp[0],arTemp[1],arTemp[2]};
                copy.getRaster().setPixel(i, j, newArray);
            }
        return copy;    
    }
    
    private void toYIQ(){
           
        double[] dArray = new double[3];
        int yArray;
        
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                
                image.getRaster().getPixel(i, j, dArray);
                
                yArray = (int) (0.299*dArray[0] + 0.587*dArray[1] + 0.114*dArray[2]);
                
                yArray = yArray - 128;
                yMatrix[i][j] = (byte) yArray;
                
            }
        }
    }
    
    public Byte[][] getYMatrix(){
        return yMatrix;
    }
    
    public int getWidth(){
        return image.getWidth();
    }
    
    public int getHeight(){
        return image.getHeight();
    }
    
     public BufferedImage getWhiteImage(int width, int height){
        BufferedImage bf = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for(int i = 0 ; i < width; i++){
            for(int j = 0; j < height; j++){
                double[] white = {255.0,255.0,255.0};
                bf.getRaster().setPixel(i, j, white);
            }   
        }
        return bf;
    }

    private static void drawArrows(BufferedImage img, double x, double y, double scale, double angle) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        double dx = Math.cos(angle) * scale;
        double dy = Math.sin(angle) * scale;
        int x2 = (int) Math.round(x + dx);
        int y2 = (int) Math.round(y + dy);
        //drawArrow(g, x, y, x2, y2);
        g.drawLine((int) Math.round(x), (int) Math.round(y), x2, y2);
        g.setColor(Color.WHITE);
        g.drawOval((int) Math.round(x), (int) Math.round(y), 0, 0);
        g.setColor(Color.WHITE);
        g.drawOval(x2, y2, 0, 0);
    }

    private static void drawRectangles(BufferedImage img, double x, double y, double scale, double angle) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        double dx = Math.cos(angle) * scale;
        double dy = Math.sin(angle) * scale;
        double xo = x, yo = y;
        int x2o = (int) Math.round(x + dx);
        int y2o = (int) Math.round(y + dy);
        if (dx < 0) {
            x += dx;
            dx = -dx;
        }
        if (dy < 0) {
            y += dy;
            dy = -dy;
        }
        g.fillRect((int) Math.round(x), (int) Math.round(y), (int) Math.round(dx), (int) Math.round(dy));
        g.drawRect((int) Math.round(x), (int) Math.round(y), (int) Math.round(dx), (int) Math.round(dy));
        g.setColor(Color.WHITE);
        g.drawOval((int) Math.round(xo), (int) Math.round(yo), 0, 0);
        g.setColor(Color.WHITE);
        g.drawOval(x2o, y2o, 0, 0);
    }
}
