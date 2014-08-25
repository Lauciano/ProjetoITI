package projetoiti;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import de.lmu.ifi.dbs.jfeaturelib.features.Sift;

// JNI from http://www.cs.ubc.ca/~lowe/keypoints/
// Para compilar do terminal:
// java -cp .:bin:lib/JFeatureLib-1.6.0-jar-with-dependencies.jar -Djava.library.path=. SIFTTest
public class SIFTTest {

    public static void main(String args[]) {
        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println("OS::" + OS);
        String filePath = "tiger.jpg";
        int width, height;
        boolean arrows = false;

        BufferedImage img;
        try {
            img = ImageIO.read(new File(filePath));
            width = img.getWidth();
            height = img.getHeight();
            System.out.println("width x height " + width + " x " + height);
            ImageProcessor ip = new ColorProcessor(img);
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
                    drawArrows(img, r[1], r[0], r[2], r[3]);
                } else {
                    drawRectangles(img, r[1], r[0], r[2], r[3]);
                }
            }

            ImageIO.write(img, "png", new File(filePath.subSequence(0, filePath.length() - 4) + "Output.png"));

            System.out.println(result.size() + " features extracted...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
