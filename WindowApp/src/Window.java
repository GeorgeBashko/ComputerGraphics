import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Window extends JFrame {
    private final JLabel label = new JLabel();
    Window(String s)
    {
        super(s);
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(label,BorderLayout.SOUTH);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel,BorderLayout.CENTER);
        //JButton button = new JButton();
        panel.setBackground(new Color(255, 0, 0));
        JSlider slider = new JSlider();
        slider.setVisible(true);
        panel.add(slider);
        slider.setVisible(true);
        slider.setMinimum(0);
        slider.setMaximum(255);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("asdasd");
            }
        });
        setStatus(2,2);
//        panel.add(button);
//        panel.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e)
//            {
//                setStatus(e.getX(),e.getY());
//                button.setBounds(e.getX(),e.getY(),20,20);
//            }
//        });
        setVisible(true);
        /*
        panel.addMouseMotionListener(new MyMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        button.add(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e)
            {
                setStatus(button.getX(),button.getY());

            }
        });
         */
    }
    void setStatus(int x,int y)
    {
        label.setText("x: " + x + "y: " + y);
    }
    public static void main(String[] args){
        new Window("Lab1");
    }
}