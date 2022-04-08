package ce326.hw2;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class YUVImage {
    private YUVPixel[][] image;

    // ******Constructors******
    public YUVImage(int width, int height) {
        image = new YUVPixel[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setPixel(i, j, new YUVPixel((short)16, (short)128, (short)128));
            }
        }
    }

    public YUVImage(YUVImage copyImg) {
        this(copyImg.getWidth(), copyImg.getHeight());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setPixel(i, j, copyImg.getPixel(i, j));
            }
        }
    }
    
    public YUVImage(RGBImage RGBImg) {
        this(RGBImg.getWidth(), RGBImg.getHeight());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setPixel(i, j, new YUVPixel(RGBImg.getPixel(i, j)));
            }
        }
    }

    public YUVImage(File file) throws FileNotFoundException,
                                      UnsupportedFileFormatException {
        if (file.exists() == false) {
            throw new FileNotFoundException();
        }
        if (file.getName().endsWith(".yuv") == false) {
            throw new UnsupportedFileFormatException();
        }
    
        Scanner sc = new Scanner(file);
        
        sc.next();
        int width = sc.nextInt();
        int height = sc.nextInt();
        
        image = new YUVPixel[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setPixel(i, j, new YUVPixel(sc.nextShort(), 
                                            sc.nextShort(), 
                                            sc.nextShort()));
            }
        }
        sc.close();
    }


    // ******Methods******
    // ***getWidth***
    public int getWidth() {
        return image[0].length;
    }

    // ***getHeight***
    public int getHeight() {
        return image.length;
    }

    // ***getPixel***
    public YUVPixel getPixel(int row, int col) {
        return image[row][col];
    }

    // ***getImage***
    public YUVPixel[][] getImage() {
        return image;
    }

    // ***setPixel***
    public void setPixel(int row, int col, YUVPixel pixel) {
        image[row][col] = pixel;
    }

    // ***setImage***
    public void setImage(YUVPixel[][] image) {
        this.image = new YUVPixel[image.length][image[0].length];
        this.image = image;
    }

    // ***toString***
    public String toString() {
        String string = "YUV3\n" + getWidth() + 
                        " " + getHeight() + "\n";

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                string += getPixel(i, j).getY() + " " +
                          getPixel(i, j).getU() + " " +
                          getPixel(i, j).getV() + "\n";
            }
        }

        return string;
    }
    
    // ***toFile***
    public void toFile(File file) {        
        if (file.exists()) {
            file.delete();
        }
        try {
            FileWriter writer = new FileWriter(file, false);

            writer.write(toString());
            writer.close();
        }
        catch (Exception e) {
            System.out.println("FileWriter exception");
        }
    }

    // ***equalize***
    public void equalize() {
        Histogram histogram = new Histogram(this);
        histogram.equalize();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                getPixel(i, j).setY(histogram.getEqualizedLuminocity(getPixel(i, j).getY()));
            }
        }
    }
}