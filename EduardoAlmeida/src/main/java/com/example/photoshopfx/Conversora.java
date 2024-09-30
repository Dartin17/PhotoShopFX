package com.example.photoshopfx;

import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import ij.ImagePlus;

public class Conversora {

    public static Image tonsCinza(Image image){
        // converte um Image em BufferedImage
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        // captura pixels da imagem
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        for (int lin = 0; lin < image.getHeight(); lin++){
            for (int col = 0; col < image.getWidth(); col++) {
                raster.getPixel(col,lin,pixel);  // obtenha um pixel
                int tonsCinza = (int) (0.299*pixel[0]+0.587*pixel[1]+0.114*pixel[2]);
                pixel[0] = pixel[1] = pixel[2] = tonsCinza;

                raster.setPixel(col,lin,pixel);  // reaplique o pixel
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    static public Image pretoBranco(Image image){
        BufferedImage bimg;
        image = Conversora.tonsCinza(image);
        bimg= SwingFXUtils.fromFXImage(image, null);
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        for (int lin = 0; lin < image.getHeight(); lin++){
            for (int col = 0; col < image.getWidth(); col++) {
                raster.getPixel(col,lin,pixel);  // obtenha um pixel
                if(((pixel[0] +pixel[1] + pixel[2])/3) >= 128) {
                    pixel[0] = pixel[1] = pixel[2] = 255;
                }
                else{
                    pixel[0] = pixel[1] = pixel[2] = 0;
                }

                raster.setPixel(col,lin,pixel);  // reaplique o pixel
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    static public Image espelharHorizontal(Image image) {
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        int pixelE[] = { 0 , 0 , 0 , 0 };
        int pixelD[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        int colF =(int) image.getWidth();
        for (int lin = 0; lin < image.getHeight(); lin++){
            for (int col = 0; col < colF/2; col++) {
                raster.getPixel(col,lin,pixelE);
                raster.getPixel(colF - col - 1,lin,pixelD);
                raster.setPixel(col,lin,pixelD);
                raster.setPixel(colF - col - 1,lin,pixelE);
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    static public Image espelharVertical(Image image) {
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        int pixelE[] = { 0 , 0 , 0 , 0 };
        int pixelD[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        int linF =(int) image.getHeight();
        for (int col = 0; col < image.getWidth(); col++){
            for (int lin = 0; lin < linF/2; lin++) {
                raster.getPixel(col,lin,pixelE);
                raster.getPixel(col,linF - lin - 1,pixelD);
                raster.setPixel(col,lin,pixelD);
                raster.setPixel(col,linF - lin - 1,pixelE);
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    static public Image negativo(Image image) {
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        for (int col = 0; col < image.getWidth(); col++){
            for (int lin = 0; lin < image.getHeight(); lin++) {
                raster.getPixel(col,lin,pixel);
                pixel[0] = 65536 - pixel[0];
                pixel[1] = 65536 - pixel[1];
                pixel[2] = 65536 - pixel[2];
                raster.setPixel(col,lin,pixel);}
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image detectarBordasIJ(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        BufferedImage bimg = SwingFXUtils.fromFXImage(image, (BufferedImage)null);
        imagePlus.setImage(bimg);
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.findEdges();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), (WritableImage)null);
    }



static public Image erosaoIJ(Image image){
        ImagePlus imagePlus = new ImagePlus();
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        imagePlus.setImage(bimg);
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.erode();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    static public Image gaussianBlurIJ(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        BufferedImage bimg;
        bimg = SwingFXUtils.fromFXImage(image, null);
        imagePlus.setImage(bimg);
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.blurGaussian(2.5);
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    static public Image dilatarIJ(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        BufferedImage bimg;
        bimg = SwingFXUtils.fromFXImage(image, null);
        imagePlus.setImage(bimg);
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.dilate();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }
}