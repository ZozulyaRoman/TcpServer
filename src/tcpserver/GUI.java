

package tcpserver;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GUI {
    private JFrame frame;
    private JScrollPane pane;
    private JTextArea textArea;

    public GUI() {
        createGUI();
    }

    private void createGUI() {
        createTextArea();
        createPane();
        createFrame();
        frame.setVisible(true);
    }

    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFocusable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

    }

    private void createPane() {
        pane = new JScrollPane(textArea);
    }

    private void createFrame() {
        frame = new JFrame("My_Messenger`s server");
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(pane);
    }
        public void sendMessToArea(String text){
            textArea.append(text);
            textArea.append("\n");
            textArea.setCaretPosition(textArea.getText().length());
    }
    
}
