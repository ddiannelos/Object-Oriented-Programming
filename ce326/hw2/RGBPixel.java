package ce326.hw2;

public class RGBPixel {
    private int rgb;

    // ******Constructors******
    public RGBPixel(short red, short green, short blue) {
        rgb = 0;
        rgb |= red << 16;
        rgb |= green << 8;
        rgb |= blue;
    }
    
    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    public RGBPixel(YUVPixel pixel) {
        short C = (short) (pixel.getY() - 16);
        short D = (short) (pixel.getU() - 128);
        short E = (short) (pixel.getV() - 128);

        short red =  clip((short) ((298*C + 409*E + 128) >> 8));
        short green = clip((short) ((298*C - 100*D - 208*E + 128) >> 8));
        short blue =  clip((short) ((298*C + 516*D + 128) >> 8));
        
        rgb = 0;
        rgb |= red << 16;
        rgb |= green << 8;
        rgb |= blue;
    }
    
    // ******Methods******
    // ***getRed***
    public short getRed() {
        return (short) ((rgb >> 16) & 0xff);
    }
    
    // ***getGreen***
    public short getGreen() {
        return (short) ((rgb >> 8) & 0xff);
    }

    // ***getBlue***
    public short getBlue() {
        return (short) (rgb & 0xff);
    }

    // ***setRed***
    void setRed(short red) {
        int newRgb = 0;

        newRgb |= red << 16;
        newRgb |= getGreen() << 8;
        newRgb |= getBlue();
        
        rgb = newRgb;
    }

    // ***setGreen***
    void setGreen(short green) {
        int newRgb = 0;

        newRgb |= getRed() << 16;
        newRgb |= green << 8;
        newRgb |= getBlue();
        
        rgb = newRgb;
    }

    // ***setBlue***
    void setBlue(short blue) {
        int newRgb = 0;

        newRgb |= getRed() << 16;
        newRgb |= getGreen() << 8;
        newRgb |= blue;
        
        rgb = newRgb;
    }

    // ***getRGB***
    public int getRGB() {
        return rgb;
    }

    // ***setRGB***
    public void setRGB(int value) {
        rgb = value;
    }

    // ***toString***
    @Override
    public String toString() {        
        return String.format("%d %d %d", getRed(), getGreen(), getBlue());
    }

    // ***clip***
    public short clip(short num) {
        if (num < 0) {
            return 0;
        }
        if (num > 255) {
            return 255;
        }

        return num;
    }
}