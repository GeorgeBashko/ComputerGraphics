import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class ColorWindow extends JFrame{
//    ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
    private JPanel panel1;
    private JSlider slider1;
    private JSlider slider2;
    private JSlider slider3;
    private JTextField textField1;

    private JPanel panel2;
    private JPanel panel3;
    private JSlider slider4;
    private JSlider slider5;
    private JSlider slider6;
    private JSlider slider7;
    private JSlider slider8;
    private JSlider slider9;
    private JSlider slider10;
    private JTextField setR;
    private JTextField setG;
    private JTextField setB;
    private JButton setColorButton;
    private JButton setColorButton1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton setColorButton2;
    private JTextField textField7;
    private JTextField textField8;
    private JButton chooseColorButton;

    private Color color;

    public ColorWindow() {
        setContentPane(panel1);
        setSize(1000,900);
        setLocationRelativeTo(null);
        textField1.setEditable(false);
        final JColorChooser chooser = new JColorChooser();
        chooser.setColor(Color.BLUE);
        chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                color = chooser.getColor();
                setRGB(color.getRed(),color.getGreen(),color.getBlue());
                setSliderValueRGB(color.getRed(),color.getGreen(),color.getBlue());
                textField1.setBackground(color);
                int[] arr = RGBtoCMYK(slider1.getValue(), slider2.getValue(),slider3.getValue());
                setCMYK(arr[0],arr[1],arr[2],arr[3]);
                float[] arr1 = RGBtoHSL(new Color(slider1.getValue(), slider2.getValue(),slider3.getValue()));
                setHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
                setSliderValueCMYK(arr[0],arr[1],arr[2],arr[3]);
                setSliderValueHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
            }
        });
        JDialog dialog = JColorChooser.createDialog(null, "Color Chooser",
                true, chooser, null, null);
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });
        setColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(setR.getText()) > 255 || Integer.parseInt(setG.getText()) > 255 || Integer.parseInt(setB.getText()) > 255 || Integer.parseInt(setR.getText()) < 0 || Integer.parseInt(setG.getText()) < 0 || Integer.parseInt(setB.getText()) < 0)
                {
                    JOptionPane.showMessageDialog(null, "Values can be only from 0 to 255!", "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    setRGB(Integer.parseInt(setR.getText()),Integer.parseInt(setG.getText()),Integer.parseInt(setB.getText()));
                    setSliderValueRGB(Integer.parseInt(setR.getText()),Integer.parseInt(setG.getText()),Integer.parseInt(setB.getText()));
                    textField1.setBackground(new Color(Integer.parseInt(setR.getText()),Integer.parseInt(setG.getText()),Integer.parseInt(setB.getText())));
                    int[] arr = RGBtoCMYK(slider1.getValue(), slider2.getValue(),slider3.getValue());
                    setCMYK(arr[0],arr[1],arr[2],arr[3]);
                    float[] arr1 = RGBtoHSL(new Color(slider1.getValue(), slider2.getValue(),slider3.getValue()));
                    setHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
                    setSliderValueCMYK(arr[0],arr[1],arr[2],arr[3]);
                    setSliderValueHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
                }
            }
        });
        setColorButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(textField2.getText()) > 100 || Integer.parseInt(textField3.getText()) > 100 || Integer.parseInt(textField4.getText()) > 100|| Integer.parseInt(textField5.getText()) > 100 || Integer.parseInt(textField2.getText()) < 0 || Integer.parseInt(textField3.getText()) < 0 || Integer.parseInt(textField4.getText()) < 0 || Integer.parseInt(textField5.getText()) < 0)
                {
                    JOptionPane.showMessageDialog(null, "Values can be only from 0 to 100!", "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    setCMYK(Integer.parseInt(textField2.getText()), Integer.parseInt(textField3.getText()), Integer.parseInt(textField4.getText()), Integer.parseInt(textField5.getText()));
                    setSliderValueCMYK(Integer.parseInt(textField2.getText()), Integer.parseInt(textField3.getText()), Integer.parseInt(textField4.getText()), Integer.parseInt(textField5.getText()));
                    Color color = cmykToRgb(slider4.getValue(), slider5.getValue(), slider6.getValue(), slider7.getValue());
                    textField1.setBackground(color);
                    //textField9.setBackground(cmykToRgb(slider4.getValue(), slider5.getValue(),slider6.getValue(),slider7.getValue()));
                    setRGB(color.getRed(), color.getGreen(), color.getBlue());
                    setCMYK(slider4.getValue(), slider5.getValue(), slider6.getValue(), slider7.getValue());
                    float[] arr = CMYKtoHSL(slider4.getValue(), slider5.getValue(), slider6.getValue(), slider7.getValue());
                    setHSL((int) arr[0], arr[1], (int) arr[2]);
                    setSliderValueRGB(color.getRed(), color.getGreen(), color.getBlue());
                    setSliderValueHSL(arr[0], arr[1], arr[2]);
                }
            }
        });
        setColorButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(textField6.getText()) > 359 || Integer.parseInt(textField7.getText()) > 100 || Integer.parseInt(textField8.getText()) > 100 || Integer.parseInt(textField6.getText()) < 0 || Integer.parseInt(textField7.getText()) < 0 || Integer.parseInt(textField8.getText()) < 0)
                {
                    JOptionPane.showMessageDialog(null, "Values can be only from 0 to 100!", "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    setHSL(Float.parseFloat(textField6.getText()), Float.parseFloat(textField7.getText()), Float.parseFloat(textField8.getText()));
                    setSliderValueHSL(Float.parseFloat(textField6.getText()), Float.parseFloat(textField7.getText()), Float.parseFloat(textField8.getText()));
                    Color color1 = HSLtoRGB(slider8.getValue(), slider9.getValue(), slider10.getValue());
                    textField1.setBackground(color1);
                    setRGB(color1.getRed(), color1.getGreen(), color1.getBlue());
                    int[] arr = HSLtoCMYK(slider8.getValue(), slider9.getValue(), slider10.getValue());
                    setCMYK(arr[0], arr[1], arr[2], arr[3]);
                    setHSL(slider8.getValue(), slider9.getValue(), slider10.getValue());
                    //Color color1 = HLStoRGB(new int[] {slider8.getValue(), slider9.getValue(),slider10.getValue()});
                    setSliderValueRGB(color1.getRed(), color1.getGreen(), color1.getBlue());
                    setSliderValueCMYK(arr[0], arr[1], arr[2], arr[3]);
                }
            }
        });
        MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                //System.out.println("123");
                textField1.setBackground(new Color(slider1.getValue(), slider2.getValue(),slider3.getValue()));
                setRGB(slider1.getValue(), slider2.getValue(),slider3.getValue());
                int[] arr = RGBtoCMYK(slider1.getValue(), slider2.getValue(),slider3.getValue());
                setCMYK(arr[0],arr[1],arr[2],arr[3]);
                float[] arr1 = RGBtoHSL(new Color(slider1.getValue(), slider2.getValue(),slider3.getValue()));
                setHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
                setSliderValueCMYK(arr[0],arr[1],arr[2],arr[3]);
                setSliderValueHSL((int)arr1[0],(int)arr1[1],(int)arr1[2]);
            }
        };
        slider1.addMouseMotionListener(mouseMotionListener);
        slider2.addMouseMotionListener(mouseMotionListener);
        slider3.addMouseMotionListener(mouseMotionListener);
        MouseMotionListener mouseMotionListener1 = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Color color = cmykToRgb(slider4.getValue(), slider5.getValue(),slider6.getValue(),slider7.getValue());
                textField1.setBackground(color);
                //textField9.setBackground(cmykToRgb(slider4.getValue(), slider5.getValue(),slider6.getValue(),slider7.getValue()));
                setRGB(color.getRed(),color.getGreen(),color.getBlue());
                setCMYK(slider4.getValue(), slider5.getValue(),slider6.getValue(),slider7.getValue());
                float[] arr = CMYKtoHSL(slider4.getValue(), slider5.getValue(),slider6.getValue(),slider7.getValue());
                setHSL((int)arr[0],arr[1],(int)arr[2]);
                setSliderValueRGB(color.getRed(),color.getGreen(),color.getBlue());
                setSliderValueHSL(arr[0],arr[1],arr[2]);
            }
        };
        slider4.addMouseMotionListener(mouseMotionListener1);
        slider5.addMouseMotionListener(mouseMotionListener1);
        slider6.addMouseMotionListener(mouseMotionListener1);
        slider7.addMouseMotionListener(mouseMotionListener1);
        MouseMotionListener mouseMotionListener2 = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Color color1 = HSLtoRGB(slider8.getValue(), slider9.getValue(),slider10.getValue());
                textField1.setBackground(color1);
                setRGB(color1.getRed(),color1.getGreen(),color1.getBlue());
                int[] arr = HSLtoCMYK(slider8.getValue(), slider9.getValue(),slider10.getValue());
                setCMYK(arr[0],arr[1],arr[2],arr[3]);
                setHSL(slider8.getValue(), slider9.getValue(),slider10.getValue());
                //Color color1 = HLStoRGB(new int[] {slider8.getValue(), slider9.getValue(),slider10.getValue()});
                setSliderValueRGB(color1.getRed(),color1.getGreen(),color1.getBlue());
                setSliderValueCMYK(arr[0],arr[1],arr[2],arr[3]);
            }
        };
        slider8.addMouseMotionListener(mouseMotionListener2);
        slider9.addMouseMotionListener(mouseMotionListener2);
        slider10.addMouseMotionListener(mouseMotionListener2);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //slider1.setEnabled(false);
        //panel1.add(panel2);
        //panel3.setVisible(false);
        //panel1.setVisible(false);
        //textField9.setBackground(cmykToRgb(10,100,10,100));
    }
//    Color color = new Color(100,100,100);
    public static void main(String[] args){
        new ColorWindow();
    }
    private static Color cmykToRgb(int c, int m, int y, int k) {
        int r = (int)(255.0 * (1.0 - (float)c/100.0) * (1.0 - (float)k/100.0));
        int g = (int)(255 * (1 - (float)m/100) * (1 - (float)k/100));
        int b = (int)(255 * (1 - (float)y/100) * (1 - (float)k/100));
        return new Color(r,g,b);
    }
    public int[] RGBtoCMYK(int r,int g,int b){
        double percentageR = r / 255.0 * 100;
        double percentageG = g / 255.0 * 100;
        double percentageB = b / 255.0 * 100;

        double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

        if (k == 100) {
            return new int[]{ 0, 0, 0, 100 };
        }

        int c = (int)((100 - percentageR - k) / (100 - k) * 100);
        int m = (int)((100 - percentageG - k) / (100 - k) * 100);
        int y = (int)((100 - percentageB - k) / (100 - k) * 100);

        return new int[]{ c, m, y, (int)k };
    }
    /////////////////////////////////////////////////////
    public float[] RGBtoHSL(Color color){
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
        System.out.println(l);
        return new float[] {h, s * 100, l * 100};
    }
    public float[] CMYKtoHSL(int c, int m, int y, int k){
        Color color1 = cmykToRgb(c,m,y,k);
        return RGBtoHSL(color1);
    }
    public int[] HSLtoCMYK(int h, int s, int l){
        Color color1 = HSLtoRGB(h, s, l);
        return RGBtoCMYK(color1.getRed(),color1.getGreen(),color1.getBlue());
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

        int[] array = { r, g, b };
        System.out.println(r + ", " + g + ", " + b);
        return new Color(r,g,b);
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
    public void setRGB(int r, int g, int b){
        setR.setText(Integer.toString(r));
        setG.setText(Integer.toString(g));
        setB.setText(Integer.toString(b));
    }
    public void setCMYK(int c, int m, int y, int k) {
        textField2.setText(Integer.toString(c));
        textField3.setText(Integer.toString(m));
        textField4.setText(Integer.toString(y));
        textField5.setText(Integer.toString(k));
    }
    public void setHSL(float h, float s, float l){
        textField6.setText(Integer.toString((int)h));
        textField7.setText(Integer.toString((int)s));
        textField8.setText(Integer.toString((int)l));
    }
    public void setSliderValueCMYK(int c, int m, int y, int k){
        slider4.setValue(c);
        slider5.setValue(m);
        slider6.setValue(y);
        slider7.setValue(k);
    }
    public void setSliderValueRGB(int r,int g, int b){
        slider1.setValue(r);
        slider2.setValue(g);
        slider3.setValue(b);
    }
    public void setSliderValueHSL(float h, float s, float l){
        slider8.setValue((int)h);
        slider9.setValue((int)s);
        slider10.setValue((int)l);
    }
}
