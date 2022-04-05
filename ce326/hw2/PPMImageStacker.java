package ce326.hw2;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class PPMImageStacker {
    private List <PPMImage> stackImg;
    private PPMImage img;
    
    // ******Constructors******
    public PPMImageStacker(File dir) throws FileNotFoundException, 
                                            UnsupportedFileFormatException{
        if (dir.exists() == false) {
            throw new FileNotFoundException("[ERROR] Directory " + dir.getName() + 
                                            " does not exist!");
        }
        if (dir.isDirectory() == false) {
            throw new FileNotFoundException ("[ERROR] " + dir.getName() +
                                            " is not a directory!");
        }
        
        stackImg = new ArrayList<PPMImage>();

        for (File temp : dir.listFiles()) {
            try {
                if (!temp.getName().startsWith(".")) {
                    PPMImage image = new PPMImage(temp);
                    stackImg.add(image);                    
                }
            }
            catch(FileNotFoundException e) {
                throw new FileNotFoundException();
            }
            catch(UnsupportedFileFormatException e) {
               throw new UnsupportedFileFormatException();
            }
        }
    }

    // ******Methods******
    // ***stack***
    public void stack() {
        int height = 0, width = 0, colordepth = 0;

        for (int i = 0; i < stackImg.size(); i++) {
            if (stackImg.get(i).getHeight() > height) {
                height = stackImg.get(i).getHeight();
            }
            if (stackImg.get(i).getWidth() > width) {
                width = stackImg.get(i).getWidth();
            }
            if (stackImg.get(i).getColorDepth() > colordepth) {
                colordepth = stackImg.get(i).getColorDepth();
            }
        }
        
        RGBImage image = new RGBImage(width, height, colordepth);
        short red = 0, green = 0, blue = 0;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < stackImg.size(); k++) {
                    red += stackImg.get(k).getPixel(i, j).getRed();
                    green += stackImg.get(k).getPixel(i, j).getGreen();
                    blue += stackImg.get(k).getPixel(i, j).getBlue();
                }
                red /= stackImg.size();
                green /= stackImg.size();
                blue /= stackImg. size();

                image.setPixel(i ,j, new RGBPixel((red < 255) ? red : 255, 
                                                  (green < 255) ? green : 255, 
                                                  (blue < 255) ? blue : 255));
            
                red = green = blue = 0;
            }
        }
        img = new PPMImage(image);
    }

    // ***getStackedImage***
    public PPMImage getStackedImage() {
        stack();
        return img;
    }
}