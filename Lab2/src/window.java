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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class window extends JFrame{
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JRadioButton медианаRadioButton;
    private JRadioButton максимумRadioButton;
    private JRadioButton минимумRadioButton;
    private JButton применитьФильтрButton;
    private JRadioButton выравниваниеRadioButton;
    private JRadioButton линейноеКонтрастированиеRadioButton;
    private JRadioButton эквализацияRGBRadioButton;
    private JRadioButton эквализацияHSBRadioButton;
    private JButton применитьЭквализациюButton;
    private JButton выбратьКартинкуButton;
    private JLabel label1;
    private JLabel label2;
    private BufferedImage mainImage;
    private String path;

    public window(){
        setContentPane(panel1);
        setSize(1000,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(выравниваниеRadioButton);
        buttonGroup.add(линейноеКонтрастированиеRadioButton);
        buttonGroup.add(эквализацияRGBRadioButton);
        buttonGroup.add(эквализацияHSBRadioButton);

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(медианаRadioButton);
        buttonGroup1.add(максимумRadioButton);
        buttonGroup1.add(минимумRadioButton);
        выбратьКартинкуButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        JFrame frame = new JFrame();
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(frame);
        String filename = fc.getSelectedFile().getPath();
        path = filename;
        label1.setLayout(new FlowLayout());
        label1.removeAll();
        label1.setVisible(false);
                try {
                    BufferedImage image = ImageIO.read(new File(filename));
                    mainImage = image;
                    image = resizeImage(image,500,500);
                    JLabel imageLabel = new JLabel(new ImageIcon(image), 0);
                    label1.add(imageLabel);
                    label1.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        применитьЭквализациюButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(линейноеКонтрастированиеRadioButton.isSelected()) {
                    try {
                        Diagram diagram = new Diagram(path, "начальная", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedImage image = linearContrast(mainImage);
                    label2.setLayout(new FlowLayout());
                    label2.removeAll();
                    label2.setVisible(false);

                    try {
                        saveToImageFile(image, "src\\linear.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Diagram diagram = new Diagram("src\\linear.jpg", "линейное контрастирование", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\linear.jpg")), 500, 500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
                else if (выравниваниеRadioButton.isSelected()){
                    try {
                        Diagram diagram = new Diagram(path, "начальная", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedImage image = equalize(getGrayscaleImage(mainImage));
                    label2.setLayout(new FlowLayout());
                    label2.removeAll();
                    label2.setVisible(false);

                    try {
                        saveToImageFile(image, "src\\equalize.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Diagram diagram = new Diagram("src\\equalize.jpg", "выравнивание", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\equalize.jpg")), 500, 500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
                else if(эквализацияRGBRadioButton.isSelected()){
                    try {
                        Diagram diagram = new Diagram(path, "начальная", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedImage image = rgbEqualize(mainImage);
                    label2.setLayout(new FlowLayout());
                    label2.removeAll();
                    label2.setVisible(false);

                    try {
                        saveToImageFile(image, "src\\rgbEqualize.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Diagram diagram = new Diagram("src\\rgbEqualize.jpg", "эквализация RGB", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\rgbEqualize.jpg")), 500, 500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
                else {
                    try {
                        Diagram diagram = new Diagram(path, "начальная", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedImage image = BrightnessEqualize(mainImage);
                    label2.setLayout(new FlowLayout());
                    label2.removeAll();
                    label2.setVisible(false);

                    try {
                        saveToImageFile(image, "src\\brightnessEqualize.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Diagram diagram = new Diagram("src\\brightnessEqualize.jpg", "эквализация HSL", height, width);
                        diagram.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\brightnessEqualize.jpg")), 500, 500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
            }
        });
        применитьФильтрButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label2.setLayout(new FlowLayout());
                label2.removeAll();
                label2.setVisible(false);
                if(медианаRadioButton.isSelected()) {
                    try {
                        getMatrix(getImageFromFile(path));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    medianFilter();
                    mainImage = getImageFromMatrix(matrix,height,width);
                    try {
                        saveToImageFile(mainImage,"src\\median.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\median.jpg")),500,500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(123);
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
                else if(максимумRadioButton.isSelected())
                {
                    try {
                        getMatrix(getImageFromFile(path));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    maxMinFilter(true);
                    mainImage = getImageFromMatrix(matrix,height,width);
                    try {
                        saveToImageFile(mainImage,"src\\median.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\median.jpg")),500,500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //System.out.println(123);
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
                else
                {
                    try {
                        getMatrix(getImageFromFile(path));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    maxMinFilter(false);
                    mainImage = getImageFromMatrix(matrix,height,width);
                    try {
                        saveToImageFile(mainImage,"src\\median.jpg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel imageLabel = null;
                    try {
                        imageLabel = new JLabel(new ImageIcon(resizeImage(ImageIO.read(new File("src\\median.jpg")),500,500)), 0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //System.out.println(123);
                    label2.add(imageLabel);
                    label2.setVisible(true);
                }
            }
        });
        setVisible(true);
    }
    private static BufferedImage resizeImage(BufferedImage originalImage,int IMG_WIDTH, int IMG_HEIGHT){
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        return resizedImage;
    }
    public static void main(String[] args){
        window window = new window();
        window.setVisible(true);
    }
    protected static int height;
    protected static int width;
    protected static int[][] matrix;

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
                bhistogram[(int)(RGBtoHSL(new Color(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y])))[2])]++;
            }
        }
        int[] bchistogram = new int[256];
        bchistogram[0] = bhistogram[0];
        for(int i=1;i<256;i++){
            bchistogram[i] = bchistogram[i-1] + bhistogram[i];
        }

        float[] barr = new float[256];
        for(int i=0;i<256;i++){
            barr[i] =  (float)((bchistogram[i] * 100.0)/(float)totpix);
        }

        for (int x = 0; x < src.getHeight(); x++) {
            for (int y = 0; y < src.getWidth(); y++) {
                int bnVal = (int) barr[(int)(RGBtoHSL(new Color(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y])))[2])];
                float[] arr = RGBtoHSL(new Color(getRed(matrix1[x][y]),getGreen(matrix1[x][y]),getBlue(matrix1[x][y])));
               // System.out.println(Arrays.toString(arr) + " " + bnVal);
                int rgb = HSLtoRGB(arr[0],arr[1],bnVal).getRGB();
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
    public static void saveToImageFile(BufferedImage image,String fileName) throws IOException {
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
    public static float[] RGBtoHSL(Color color){
        float[] rgb = color.getRGBColorComponents( null );
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];

        //	Minimum and Maximum RGB values are used in the HSL calculations

        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        //  Calculate the Hue

        float h = 0;

        if (max == min)
            h = 0;
        else if (max == r)
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        else if (max == g)
            h = (60 * (b - r) / (max - min)) + 120;
        else if (max == b)
            h = (60 * (r - g) / (max - min)) + 240;

        //  Calculate the Luminance

        float l = (max + min) / 2;
        //System.out.println(max + " : " + min + " : " + l);

        //  Calculate the Saturation

        float s = 0;

        if (max == min)
            s = 0;
        else if (l <= .5f)
            s = (max - min) / (max + min);
        else
            s = (max - min) / (2 - max - min);
       // System.out.println(l);
        return new float[] {h, s * 100, l * 100};
    }
    private static float HueToRGB(float p, float q, float h) {
        if (h < 0)
            h += 1;

        if (h > 1)
            h -= 1;

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }
    public static Color HSLtoRGB(float h, float s, float l) {

        //  Formula needs all values between 0 - 1.

        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        int r = Math.round(Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)) * 255));
        int g = Math.round(Math.max(0, HueToRGB(p, q, h) * 255));
        int b = Math.round(Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)) * 255));

        //int[] array = { r, g, b };
     //   System.out.println(r + ", " + g + ", " + b);
        return new Color(r,g,b);
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
}
class Diagram extends JFrame
{
    private static int[][] matrix;
    private static int height;
    private static int width;

    public Diagram(String path,String name,int h,int w) throws IOException {
        matrix = new int[h][w];
        height = h;
        width = w;
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
    public static void getMatrix(BufferedImage image){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = image.getRGB(j, i) & 0xFFFFFF;
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
    public static BufferedImage getImageFromFile(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        height = image.getHeight();
        width = image.getWidth();
        matrix = new int[height][width];
        return image;
    }
}
