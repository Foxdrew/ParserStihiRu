package org.foxdrew;

import org.foxdrew.utils.Tuple2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Image {
    private BufferedImage image;
    private Image(BufferedImage image) {
        this.image = image;
    }

    public static Image download(String url) throws IOException {
        BufferedImage bi = RequestHelper.getImage(url);
        return new Image(bi);
    }

    public ByteArrayInputStream getByteArrayInputStream() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public Tuple2<Double, Double> getResizedSize(double maxWidth, double maxHeight) {
        double ratio;
        Tuple2<Double, Double> size = new Tuple2<>(maxWidth, maxHeight);

        if (image.getWidth() > image.getHeight()) {
            ratio = (double) image.getHeight() / (double) image.getWidth();
            size.setSecond(maxWidth * ratio);
        }
        else {
            ratio = (double) image.getWidth() / (double) image.getHeight();
            size.setFirst(maxHeight * ratio);
        }

        return size;
    }
}
