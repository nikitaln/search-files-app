
import com.master.display.MainForm;
import com.master.pdf.PdfFileService;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class App {

    public static void main(String[] args) {

//        PdfFileService pdfService = new PdfFileService();
//        pdfService.searchTheEarliestDate("O:\\МПЦ\\9. Архив сканирование общее\\Архив 2024\\Экспертные заключения"); //14.05.2024



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
