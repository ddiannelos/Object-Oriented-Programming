package ce326.hw2;

public class RGBPixel {
    private byte red;
    private byte green;
    private byte blue;

    // ***RGBPixel***
    public RGBPixel(short red, short green, short blue) {
        this.red = (byte) (red - 128);
        this.green = (byte) (green - 128);
        this.blue = (byte) (blue - 128);
    }
    
    // ***RGBPixel***
    public RGBPixel(RGBPixel pixel) {
        red = (byte) (pixel.getRed() - 128);
        green = (byte ) (pixel.getGreen() - 128);
        blue = (byte ) (pixel.getBlue() - 128);
    }
    
    // public RGBPixel(YUVPixel pixel) {

    // }
    
    // ***getRed***
    public short getRed() {
        return (short) (red + 128);
    }
    
    // ***getGreen***
    public short getGreen() {
        return (short) (green + 128);
    }

    // ***getBlue***
    public short getBlue() {
        return (short) (blue + 128);
    }

    // ***setRed***
    void setRed(short red) {
        this.red = (byte) (red - 128);
    }

    // ***setGreen***
    void setGreen(short green) {
        this.green = (byte) (green - 128);
    }

    // ***setBlue***
    void setBlue(short blue) {
        this.blue = (byte) (blue - 128);
    }

    // ***getRGB***
    public int getRGB() {
        int value = 0;

        value |= red << 8;
        value |= green << 4;
        value |= blue;

        return value;
    }

    // ***setRGB***
    public void setRGB(int value) {
        blue = (byte) (value & 255);
        value >>= 8;
        green = (byte) (value & 255);
        value >>= 8;
        red = (byte) (value & 255);
    }

    // ***toString***
    public String toString() {        
        return String.format("%d %d %d", red+128, green + 128, blue + 128);
    }
}