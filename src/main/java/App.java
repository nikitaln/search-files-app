import javax.swing.*;

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




//        SearchFile searchFile = new SearchFile();
//        searchFile.searchFolderWithPDF("O:\\МПЦ\\9. Архив сканирование общее\\Архив 2024");
//        searchFile.addPdfFilesToList();
//        searchFile.printAllPdfFiles();

    }
}