package ce326.hw2;

public class YUVPixel {
    private short Y;
    private short U;
    private short V;

    // ******Constructors******
    public YUVPixel(short Y, short U, short V) {
        this.Y = Y;
        this.U = U;
        this.V = V;
    }

    public YUVPixel(YUVPixel pixel) {
        this(pixel.getY(), pixel.getU(), pixel.getV());
    }
    
    public YUVPixel(RGBPixel pixel) {
        this((short) (((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128) >> 8) + 16),
             (short) (((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128) >> 8) + 128),
             (short) (((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128) >> 8) + 128));
    }
    

    // ******Methods******
    // ***getY***
    public short getY() {
        return Y;
    }

    // ***getU***
    public short getU() {
        return U;
    }

    // ***getV***
    public short getV() {
        return V;
    }

    // ***setY***
    public void setY(short Y) {
        this.Y = Y;
    }

    // ***setU***
    public void setU(short U) {
        this.U = U;
    }

    // ***setV***
    public void setV(short V) {
        this.V = V;
    }
}