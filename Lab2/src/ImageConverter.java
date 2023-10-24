import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ImageConverter extends JFrame {
    private static int height;
    private static int width;
    private static int[][] matrix;
    public static void main(String[] args) throws IOException {

//        getMatrix(getImageFromFile("src\\im4.jpg"));
//        maxMinFilter(false);
//        BufferedImage image;
//        image = getImageFromMatrix(matrix,height,width);
//        saveToImageFile(image,"src\\min.jpg");
//
//        getMatrix(getImageFromFile("src\\im2.jpg"));
//        medianFilter();
//        image = getImageFromMatrix(matrix,height,width);
//        saveToImageFile(image,"src\\median.jpg");
//
//        getMatrix(getImageFromFile("src\\im3.jpg"));
//        maxMinFilter(true);
//        image = getImageFromMatrix(matrix,height,width);
//        saveToImageFile(image,"src\\max.jpg");
        BufferedImage image4 = BrightnessEqualize(getImageFromFile("src\\im4.jpg"));
        BufferedImage image3 = rgbEqualize(getImageFromFile("src\\im2.jpg"));
        BufferedImage image1 = getGrayscaleImage(getImageFromFile("src\\im2.jpg"));
        saveToImageFile(image3,"src\\rgbEqualize.jpg");
        saveToImageFile(image1,"src\\bw.jpg");
        saveToImageFile(image4,"src\\brightnessEqualize.jpg");
        BufferedImage image2 = linearContrast(image1);
        image1 = equalize(image1);
        saveToImageFile(image1,"src\\equalize.jpg");
        saveToImageFile(image2,"src\\linear.jpg");


        ImageConverter imageConverter = new ImageConverter("src\\bw.jpg","исходное");
        imageConverter.setVisible(true);
        ImageConverter imageConverter1 = new ImageConverter("src\\equalize.jpg", "выравнивание гистограммы");
        imageConverter1.setVisible(true);
        ImageConverter imageConverter2 = new ImageConverter("src\\linear.jpg","линейное контратсирование");
        imageConverter2.setVisible(true);
    }
    public ImageConverter(String path,String name) throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        getMatrix(getImageFromFile(path));
        JFreeChart chart = createChart(createDataset(),name);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        add(panel);
        panel.setVisible(true);
        setVisible(true);
    }
    public DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        TreeMap<Float,Integer> map = new TreeMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float lightness = Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null)[2] * 255;
                if(map.containsKey(lightness)){
                    map.replace(lightness,map.get(lightness) + 1);
                }
                else map.put(lightness,0);
            }
        }
        for (Map.Entry<Float, Integer> item : map.entrySet())
            dataset.addValue(item.getValue()," ",Float.toString(item.getKey()));
        return dataset;
    }
    public static BufferedImage getGrayscaleImage(BufferedImage src) {
        BufferedImage gImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster gr = gImg.getRaster();
        for(int i=0;i<wr.getWidth();i++){
            for(int j=0;j<wr.getHeight();j++){
                gr.setSample(i, j, 0, wr.getSample(i, j, 0));
            }
        }
        gImg.setData(gr);
        return gImg;
    }
    public static BufferedImage equalize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster er = nImg.getRaster();
        int totpix= wr.getWidth()*wr.getHeight();

        int[] histogram = new int[256];

        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                histogram[wr.getSample(x, y, 0)]++;
            }
        }

        int[] chistogram = new int[256];
        chistogram[0] = histogram[0];
        for(int i=1;i<256;i++){
            chistogram[i] = chistogram[i-1] + histogram[i];
        }

        float[] arr = new float[256];
        for(int i=0;i<256;i++){
            arr[i] =  (float)((chistogram[i]*255.0)/(float)totpix);
        }

        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                int nVal = (int) arr[wr.getSample(x, y, 0)];
                er.setSample(x, y, 0, nVal);
            }
        }
        nImg.setData(er);
        return nImg;
    }
    public static BufferedImage BrightnessEqualize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix1 = new int[src.getHeight()][src.getWidth()];
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                matrix1[i][j] = src.getRGB(j, i) & 0xFFFFFF;
            }
        }

        int totpix= src.getWidth() * src.getHeight();

        int[] bhistogram = new int[256];

        for (int x = 0; x < src.getHeight(); x++) {
            for (int y = 0; y < src.getWidth(); y++) {
                bhistogram[(int)(255*Color.RGBtoHSB(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y]),null)[2])]++;
            }
        }
        int[] bchistogram = new int[256];
        bchistogram[0] = bhistogram[0];
        for(int i=1;i<256;i++){
            bchistogram[i] = bchistogram[i-1] + bhistogram[i];
            //System.out.println(bchistogram[i]);
        }

        float[] barr = new float[256];
        for(int i=0;i<256;i++){
            barr[i] =  (float)((bchistogram[i]*255.0)/(float)totpix);
            //System.out.println(barr[i]);
        }

        for (int x = 0; x < src.getHeight(); x++) {
            for (int y = 0; y < src.getWidth(); y++) {
                int bnVal = (int) barr[(int)(255*Color.RGBtoHSB(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y]),null)[2])];
                float[] arr = Color.RGBtoHSB(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y]),null);
                int rgb = Color.HSBtoRGB(arr[0],arr[1],bnVal);
                matrix1[x][y] = rgb;
                nImg.setRGB(y,x,matrix1[x][y]);
            }
        }
        return nImg;
    }
    public static BufferedImage rgbEqualize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix1 = new int[src.getHeight()][src.getWidth()];
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                matrix1[i][j] = src.getRGB(j, i) & 0xFFFFFF;
            }
        }

        int totpix= src.getWidth() * src.getHeight();

        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for (int x = 0; x < src.getHeight(); x++) {
            for (int y = 0; y < src.getWidth(); y++) {
                rhistogram[getRed(matrix1[x][y])]++;
                ghistogram[getGreen(matrix1[x][y])]++;
                bhistogram[getBlue(matrix1[x][y])]++;
            }
        }
        int[] rchistogram = new int[256];
        int[] gchistogram = new int[256];
        int[] bchistogram = new int[256];
        rchistogram[0] = rhistogram[0];
        gchistogram[0] = ghistogram[0];
        bchistogram[0] = bhistogram[0];
        for(int i=1;i<256;i++){
            rchistogram[i] = rchistogram[i-1] + rhistogram[i];
            gchistogram[i] = gchistogram[i-1] + ghistogram[i];
            bchistogram[i] = bchistogram[i-1] + bhistogram[i];
        }

        float[] rarr = new float[256];
        float[] barr = new float[256];
        float[] garr = new float[256];
        for(int i=0;i<256;i++){
            rarr[i] =  (float)((rchistogram[i]*255.0)/(float)totpix);
            garr[i] =  (float)((gchistogram[i]*255.0)/(float)totpix);
            barr[i] =  (float)((bchistogram[i]*255.0)/(float)totpix);
        }

        for (int x = 0; x < src.getHeight(); x++) {
            for (int y = 0; y < src.getWidth(); y++) {
                int rnVal = (int) rarr[getRed(matrix1[x][y])];
                int gnVal = (int) garr[getGreen(matrix1[x][y])];
                int bnVal = (int) barr[getBlue(matrix1[x][y])];
                float[] arr = Color.RGBtoHSB(rnVal,gnVal,bnVal,null);
                int rgb = Color.HSBtoRGB(arr[0],arr[1],arr[2]);
                matrix1[x][y] = rgb;
                nImg.setRGB(y,x,matrix1[x][y]);
            }
        }
        return nImg;
    }
    public static BufferedImage linearContrast(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster er = nImg.getRaster();
        int fmax = 0;
        int fmin = 257;
        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                if(wr.getSample(x, y, 0) > fmax)
                    fmax = wr.getSample(x, y, 0);
                if (wr.getSample(x, y, 0) < fmin)
                    fmin = wr.getSample(x, y, 0);
            }
        }
        System.out.println(fmin + " " + fmax);
        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                int nVal = 255 * (wr.getSample(x, y, 0) - fmin) / (fmax - fmin);
                er.setSample(x, y, 0, nVal);
            }
        }
        nImg.setData(er);
        return nImg;
    }
    public static void getMatrix(BufferedImage image){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = image.getRGB(j, i) & 0xFFFFFF;
            }
        }
    }
    private static void saveToImageFile(BufferedImage image,String fileName) throws IOException {
        File output = new File(fileName);
        try {
            ImageIO.write(image, "jpg", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage getImageFromFile(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        height = image.getHeight();
        width = image.getWidth();
        matrix = new int[height][width];
        return image;
    }
    public static BufferedImage getImageFromMatrix(int[][] matrix, int height, int width){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                image.setRGB(j, i, matrix[i][j]);
        return image;
    }
    public static void medianFilter(){
        float averageLightness = 0;
        float lightness = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                lightness += Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null)[2];
            }
        }
        averageLightness = lightness/(height * width);
        System.out.println(averageLightness);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float[] arr = Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null);
                int rgb = Color.HSBtoRGB(arr[0],arr[1],averageLightness);
                matrix[i][j] = rgb;
            }
        }
    }
    public static void maxMinFilter(boolean isMax){
        float maxLightness = 0;
        if (!isMax) maxLightness = 101;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isMax)
                    maxLightness = Math.max(maxLightness,Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null)[2]);
                else maxLightness = Math.min(maxLightness,Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null)[2]);
            }
        }
        System.out.println(maxLightness);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float[] arr = Color.RGBtoHSB(getRed(matrix[i][j]),getGreen(matrix[i][j]),getBlue(matrix[i][j]),null);
                int rgb = Color.HSBtoRGB(arr[0],arr[1],maxLightness);
                matrix[i][j] = rgb;
            }
        }
    }
    public static int getRed(int r){
        return (r >> 16) & 255;
    }
    public static int getGreen(int g){
        return (g >> 8) & 255;
    }
    public static int getBlue(int b){
        return (b) & 255;
    }
    private static JFreeChart createChart(CategoryDataset dataset, String name)
    {
        JFreeChart chart = ChartFactory.createBarChart(
                "" +
                        "Гистограмма изображения ( " + name + ")",
                "Яркость",                   // x-axis label
                "Количество",                // y-axis label
                dataset);
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
}
