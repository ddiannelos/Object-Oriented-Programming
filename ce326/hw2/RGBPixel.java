package ce326.hw2;

public class RGBPixel {
    private byte red;
    private byte green;
    private byte blue;

    // ******Constructors******
    public RGBPixel(short red, short green, short blue) {
        this.red = (byte) (red - 128);
        this.green = (byte) (green - 128);
        this.blue = (byte) (blue - 128);
    }
    
    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    // public RGBPixel(YUVPixel pixel) {

    // }
    
    // ******Methods******
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

        value |= red << 16;
        value |= green << 8;
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