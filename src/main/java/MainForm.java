import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

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


    public MainForm() {

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        System.out.println("До прогресс бара");
                        progressBar.setIndeterminate(true);
                        System.out.println("После прогресс бара");
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
    }


    public void scanningPdf() {
        System.out.println("Зашли в метод Инфо");
        String path = mainPathToDirectoryField.getText();
        String date = dateField.getText();

        System.out.println("path " + path);
        System.out.println("date " + date);


        SearchFile searchFile = new SearchFile();
        List<String> paths = searchFile.searchFolderWithPDF(path, date);
        List<PdfFile> PDFs = searchFile.addPdfFilesToList(paths);

        int count = 0;
        long pagesCount = 0;


        for (PdfFile file : PDFs) {
            if (file.getTimeCreation().equals(date)) {
                System.out.println(file.toString());
                count = count + 1;
                pagesCount = pagesCount + file.getPageCount();

            }
        }

        filesCountField.setText(String.valueOf(count));
        pagesCountField.setText(String.valueOf(pagesCount));

    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
