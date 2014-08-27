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
import javax.imageio.ImageIO;

/**
 *
 * @author Thiago
 */
public class Imagem {
    
    private BufferedImage image;
    private BufferedImage imageGray;
    private Byte[][] yMatrix;
    
    public Imagem(String end){
        //Abertura
        try {
            image = ImageIO.read(new File(end));
        } catch (IOException ex) {
            System.err.println("Problemas na leitura da imagem! Verifique o caminho.");
        }
        
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
        
        //Cinzação
        yMatrix = new Byte[image.getWidth()][image.getHeight()];
        toYIQ();
    }
    
    private void toYIQ(){
           
        double[] dArray = new double[3];
        int yArray;
        //double[] yiqArray = new double[3];
        
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                
                image.getRaster().getPixel(i, j, dArray);
                
                yArray = (int) (0.299*dArray[0] + 0.587*dArray[1] + 0.114*dArray[2]);
                //yiqArray[0] = 0.299*dArray[0] + 0.587*dArray[1] + 0.114*dArray[2];
                //yiqArray[1] = 0.596*dArray[0] - 0.275*dArray[1] - 0.321*dArray[2];
                //yiqArray[2] = 0.212*dArray[0] - 0.523*dArray[1] + 0.311*dArray[2];
                
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

    private static void drawArrows(BufferedImage img, double x, double y, double scale, double angle) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        double dx = Math.cos(angle) * scale;
        double dy = Math.sin(angle) * scale;
        int x2 = (int) Math.round(x + dx);
        int y2 = (int) Math.round(y + dy);
        //drawArrow(g, x, y, x2, y2);
        g.drawLine((int) Math.round(x), (int) Math.round(y), x2, y2);
        g.setColor(Color.RED);
        g.drawOval((int) Math.round(x), (int) Math.round(y), 0, 0);
        g.setColor(Color.GREEN);
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
        g.setColor(Color.RED);
        g.drawOval((int) Math.round(xo), (int) Math.round(yo), 0, 0);
        g.setColor(Color.GREEN);
        g.drawOval(x2o, y2o, 0, 0);
    }
}
