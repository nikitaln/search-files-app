
import com.master.display.MainForm;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class App {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        frame.setTitle("PDF-analyzer");

        MainForm mf = new MainForm();
        frame.add(mf.getMainPanel());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
