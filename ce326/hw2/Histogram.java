package ce326.hw2;

import java.io.File;
import java.io.FileWriter;

public class Histogram {
    private short[] histogram;
    private int imgPixels;

    // ******Constructors******
    public Histogram(YUVImage img) {
        histogram = new short[236];
        imgPixels = img.getWidth()*img.getHeight();

        for (int i = 0; i < 236; i++) {
            histogram[i] = 0;
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                histogram[img.getPixel(i, j).getY()]++;
            }
        }
    }

    // ******Methods******
    // ***toString***
    public String toString() {
        String string = "";

        for (int i = 0; i < 236; i++) {            
            string += String.format("\n%3d.(%4d)\t", 
                                        i, histogram[i]);
            
            int j, num = histogram[i];
            
            for (j = 0; j < num/1000; j++) {
                string += "#";
            }
            num -= j*1000;
            
            for (j = 0; j < num/100; j++) {
                string += "$";
            }
            num -= j*100;
            
            for (j = 0; j < num/10; j++) {
                string += "@";
            }
            num -= j*10;
            
            for (j = 0; j < num; j++) {
                string += "*";
            }
        }
        
        string += "\n";

        return string;
    }

    // ***toFile***
    public void toFile(File file) {
        try {
            FileWriter writer = new FileWriter(file, false);

            writer.write(toString());
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            System.out.println("FileWriter exception");
        }
    }

    // ***equalize***
    public void equalize() {
        short[] probability = new short[236];
        
        probability[0] = histogram[0];

        for (int i = 1; i < 236; i++) {
            probability[i] = (short) (histogram[i] + probability[i-1]);
        }
        
        for (int i = 0; i < 236; i++) {
            histogram[i] = (short) ((probability[i]*235) / imgPixels);
        }
    }

    // ***getEqualizedLuminocity***
    public short getEqualizedLuminocity(int luminocity) {
        return histogram[luminocity];
    }
}