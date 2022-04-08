package ce326.hw2;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class PPMImage extends RGBImage {  
    // ******Constructors******  
    public PPMImage(File file) throws FileNotFoundException, 
                                      UnsupportedFileFormatException {
        super();
        
        if (file.exists() == false || file.canRead() == false) { 
            throw new FileNotFoundException();
        }
        
        if (file.getName().endsWith(".ppm") == false) {
            throw new UnsupportedFileFormatException();
        }
        
        Scanner sc = new Scanner(file);
        
        sc.next();     
        int width = sc.nextInt();
        int height = sc.nextInt();
        
        RGBPixel[][] image = new RGBPixel[height][width];
        
        super.setImage(image);
        super.setColorDepth(sc.nextInt());
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                super.setPixel(i, j, new RGBPixel(sc.nextShort(), 
                                                  sc.nextShort(), 
                                                  sc.nextShort()));
            }
        }
        
        sc.close();
    }

    public PPMImage(RGBImage img) {
        super(img);
    }

    public PPMImage(YUVImage img) {
        super(img);
    }

    // ******Methods******
    // ***toString***
    public String toString() {
        String string = "P3\n" + super.getWidth() + 
                        " " + super.getHeight() + " " + 
                        super.getColorDepth() + "\n";

        for (int i = 0; i < super.getHeight(); i++) {
            for (int j = 0; j < super.getWidth(); j++) {
                string += super.getPixel(i, j).getRed() + " " +
                          super.getPixel(i, j).getGreen() + " " +
                          super.getPixel(i, j).getBlue() + "\n";
            }
        }

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
}