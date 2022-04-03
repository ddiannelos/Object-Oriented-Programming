package ce326.hw2;

public class RGBImage implements Image {
    public static final int MAX_COLORDEPTH = 255;
    
    private RGBPixel[][] image;
    private int colordepth;
    
    // ******Constructors******
    public RGBImage() {
    }
    
    public RGBImage(int width, int height, int colordepth) {
        image = new RGBPixel[height][width];
        this.colordepth = colordepth;
    }

    public RGBImage(RGBImage copyImg) {
        this(copyImg.getWidth(), copyImg.getHeight(), copyImg.getColorDepth());
        
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {               
                setPixel(i, j, copyImg.getPixel(i, j));
            }
        }
    }
    
    public RGBImage(YUVImage YUVImage) {
        this(YUVImage.getWidth(), YUVImage.getHeight(), 255);

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                image[i][j] = new RGBPixel(YUVImage.getPixel(i, j));
            }
        }
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

    // ***getColorDepth***
    public int getColorDepth() {
        return colordepth;
    }

    // ***getPixel****
    public RGBPixel getPixel(int row, int col) {
        return image[row][col];
    }

    // ***getImage***
    public RGBPixel[][] getImage() {
        return image;
    }

    // ***setColorDepth***
    public void setColorDepth(int colordepth) {
        this.colordepth = colordepth;
    }

    // ***setPixel***
    public void setPixel(int row, int col, RGBPixel pixel) {
        image[row][col] = pixel;
    }


    // ***setImage***
    public void setImage(RGBPixel[][] image) {
        this.image = new RGBPixel[image.length][image[0].length];
        this.image = image;
    }
    
    // ***grayscale***
    public void grayscale() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                short gray = (short) ((image[i][j].getRed() * 0.3) + 
                                     (image[i][j].getGreen() * 0.59) + 
                                     (image[i][j].getBlue() * 0.11));
                image[i][j].setRed(gray);
                image[i][j].setGreen(gray);
                image[i][j].setBlue(gray);
            }
        }
    }

    // ***doublesize***
    public void doublesize() {
        RGBImage copyImg = new RGBImage(this);  
  
        image = new RGBPixel[copyImg.getHeight()*2][copyImg.getWidth()*2];

        for (int i = 0; i < copyImg.getHeight(); i++) {
            for (int j = 0; j < copyImg.getWidth(); j++) {
                RGBPixel pixel = copyImg.getPixel(i, j);
                setPixel(2*i, 2*j, pixel);
                setPixel((2*i)+1, 2*j, pixel);
                setPixel(2*i, (2*j)+1, pixel);
                setPixel((2*i)+1, (2*j)+1, pixel);
            }
        }
    }

    // ***halfsize***
    public void halfsize() {
        RGBImage copyImg = new RGBImage(this);
        
        image = new RGBPixel[copyImg.getHeight()/2][copyImg.getWidth()/2];

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                short red = (short) ((copyImg.getPixel(2*i, 2*j).getRed() + 
                            copyImg.getPixel((2*i)+1, 2*j).getRed() + 
                            copyImg.getPixel(i, (2*j)+1).getRed() + 
                            copyImg.getPixel((2*i)+1, (2*j)+1).getRed())/4);
                short green = (short) ((copyImg.getPixel(2*i, 2*j).getGreen() + 
                            copyImg.getPixel((2*i)+1, 2*j).getGreen() + 
                            copyImg.getPixel(i, (2*j)+1).getGreen() + 
                            copyImg.getPixel((2*i)+1, (2*j)+1).getGreen())/4);;
                short blue = (short) ((copyImg.getPixel(2*i, 2*j).getBlue() + 
                            copyImg.getPixel((2*i)+1, 2*j).getBlue() + 
                            copyImg.getPixel(i, (2*j)+1).getBlue() + 
                            copyImg.getPixel((2*i)+1, (2*j)+1).getBlue())/4);;
                RGBPixel pixel = new RGBPixel(red, green, blue);
                
                setPixel(i, j, pixel);
            }
        }
    }

    // ***rotateClockwise***
    public void rotateClockwise() {
        RGBImage copyImg = new RGBImage(this);

        image = new RGBPixel[copyImg.getWidth()][copyImg.getHeight()];

        for (int i = 0; i < copyImg.getHeight(); i++) {
            for (int j = 0; j < copyImg.getWidth(); j++) {
                setPixel(j, getWidth()-1-i, copyImg.getPixel(i, j));
            }
        }
    }
}