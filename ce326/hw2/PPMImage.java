package ce326.hw2;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class PPMImage extends RGBImage {  
    // ******Constructors******
    public PPMImage(Scanner sc) {
        super(sc.nextInt(), sc.nextInt(), sc.nextInt());

    }
    
    public PPMImage(File file) throws FileNotFoundException, 
                                              UnsupportedFileFormatException {
        super(1, 1, 255);
        
        if (file.exists() == false || file.canRead() == false) { 
            throw new FileNotFoundException();
        }
        
        if (file.getName().endsWith(".ppm") == false) {
            throw new UnsupportedFileFormatException();
        }
        
        Scanner sc = new Scanner(file);
        
        sc.next();     
        RGBPixel[][] image = new RGBPixel[sc.nextInt()][sc.nextInt()];
        
        super.setImage(image);
        super.setColorDepth(sc.nextInt());
    
        short red, green, blue;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                red = sc.nextShort();
                green = sc.nextShort();
                blue = sc.nextShort();

                super.setPixel(i, j, new RGBPixel(red, green, blue));
            }
        }
        
        sc.close();
    }

    public PPMImage(RGBImage img) {
        super(img);
    }

    // public PPMImage(YUVImage img) {

    // }

    // ******Methods******
    // ***toString***
    public String toString() {
        String string = "P3\n" + super.getWidth() + 
                        " " + super.getHeight() + "\n" + 
                        super.getColorDepth() + "\n";

        for (int i = 0; i < super.getHeight(); i++) {
            for (int j = 0; j < super.getWidth(); j++) {
                string.concat(super.getPixel(i, j).getRed() + " " +
                              super.getPixel(i, j).getGreen() + " " +
                              super.getPixel(i, j).getBlue() + "\n");
            }
        }

        return string;
    }

    // ***toFile***
    public void toFile(File file) {        
        try {
            file.delete();
            file.createNewFile();

            FileWriter writer = new FileWriter(file.getName());

            writer.write(toString());
            writer.close();
        }
        catch (Exception e) {
            System.out.println("FileWriter exception");
        }
    }
}