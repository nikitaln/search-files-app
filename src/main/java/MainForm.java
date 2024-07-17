import com.master.db.DbConnection;
import com.master.pdf.PdfFile;
import com.master.pdf.PdfFileService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MainForm {

    private JPanel mainPanel;
    private JPanel managePanel;
    private JLabel titleLabel;
    private JTextField mainPathToDirectoryField;
    private JLabel mainPathToDirectoryLabel;
    private JButton searchButton;
    private JTextField filesCountField;
    private JTextField pagesCountField;
    private JTextField dateField;
    private JLabel filesCountLabel;
    private JLabel pagesCountLabel;
    private JLabel dateLabel;
    private JLabel statisticsLabel;
    private JProgressBar progressBar;
    private JButton saveDbButton;
    private JLabel resultSaveDBLabel;

    private PdfFileService searchPdfFile;
    private DbConnection dbConnection;


    public MainForm() {


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {

                        progressBar.setIndeterminate(true);

                        scanningPdf();

                        return null;
                    }
                    @Override
                    public void done() {
                        progressBar.setIndeterminate(false);
                    }
                };
                worker.execute();
            }
        });


        saveDbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * TODO: insert to db
                 */

                System.out.println("connect to DB");
                String sql = searchPdfFile.getAllFiles();

                System.out.println(sql);

                dbConnection = new DbConnection();
                dbConnection.getConnection();
                try {
                    dbConnection.executeMultiInsert(sql);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                resultSaveDBLabel.setVisible(true);
            }
        });
    }


    public void scanningPdf() {

        String path = mainPathToDirectoryField.getText();
        String date = dateField.getText();

        System.out.println("path " + path);
        System.out.println("date " + date);

        searchPdfFile = new PdfFileService();
        List<String> paths = searchPdfFile.searchFolderWithPDF(path, date);
        List<PdfFile> PDFs = searchPdfFile.addPdfFilesToList(paths);

        int count = 0;
        long pagesCount = 0;

        System.out.println("Перечисление всех файлов");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter); //переводим дату из String в объект LocalDate
        for (PdfFile file : PDFs) {
            if (file.getTimeCreation().equals(localDate)) {
                System.out.println(file.toString());
                count = count + 1;
                pagesCount = pagesCount + file.getPageCount();

            }
        }

        filesCountField.setText(String.valueOf(count));
        pagesCountField.setText(String.valueOf(pagesCount));


        dbConnection = new DbConnection();
        dbConnection.getConnection();

        try {
            System.out.println("after insert");
            String sql = searchPdfFile.getAllFiles();
            dbConnection.executeMultiInsert(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
