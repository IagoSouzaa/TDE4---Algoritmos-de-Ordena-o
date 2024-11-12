import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TestCSVReader {

    private static DefaultTableModel tableModel;
    private static final DecimalFormat df = new DecimalFormat("#.###");

    public static void main(String[] args) {
       
        JFrame frame = new JFrame("Comparação de Algoritmos de Ordenação");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        String[] columns = {"Tipo de Conjunto de Dados", "BubbleSort (ms)", "InsertionSort (ms)", "QuickSort (ms)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        
        JPanel buttonPanel = new JPanel();
        JButton bubbleSortButton = new JButton("BubbleSort");
        JButton insertionSortButton = new JButton("InsertionSort");
        JButton quickSortButton = new JButton("QuickSort");

        
        bubbleSortButton.addActionListener(e -> runAlgorithm("Bubble Sort", Sorts::bubbleSort));
        insertionSortButton.addActionListener(e -> runAlgorithm("Insertion Sort", Sorts::insertionSort));
        quickSortButton.addActionListener(e -> runAlgorithm("Quick Sort", arr -> Sorts.quickSort(arr, 0, arr.length - 1)));

       
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(insertionSortButton);
        buttonPanel.add(quickSortButton);

      
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);

       
        runTests();
    }

    private static void runTests() {
        String[] files = {
                "C:/Users/User/Desktop/TDE4/aleatorio_100.csv",
                "C:/Users/User/Desktop/TDE4/aleatorio_1000.csv",
                "C:/Users/User/Desktop/TDE4/aleatorio_10000.csv",
                "C:/Users/User/Desktop/TDE4/crescente_100.csv",
                "C:/Users/User/Desktop/TDE4/crescente_1000.csv",
                "C:/Users/User/Desktop/TDE4/crescente_10000.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_100.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_1000.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_10000.csv"
        };

        for (String file : files) {
            String dataType = getDataTypeFromFileName(file);
            try {
                int[] data = CSVReader.readCSV(file);

                
                double bubbleTime = testSortingAlgorithm(data, Sorts::bubbleSort);
                double insertionTime = testSortingAlgorithm(data, Sorts::insertionSort);
                double quickTime = testSortingAlgorithm(data, arr -> Sorts.quickSort(arr, 0, arr.length - 1));

                
                tableModel.addRow(new Object[]{dataType, df.format(bubbleTime), df.format(insertionTime), df.format(quickTime)});

            } catch (IOException e) {
                tableModel.addRow(new Object[]{dataType, "Erro", "Erro", "Erro"});
            }
        }
    }

    private static double testSortingAlgorithm(int[] originalData, SortingAlgorithm algorithm) {
        int[] data = originalData.clone();
        long startTime = System.nanoTime();
        algorithm.sort(data);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0; 
    }

    private static void runAlgorithm(String algorithmName, SortingAlgorithm algorithm) {
        String[] files = {
                "C:/Users/User/Desktop/TDE4/aleatorio_100.csv",
                "C:/Users/User/Desktop/TDE4/aleatorio_1000.csv",
                "C:/Users/User/Desktop/TDE4/aleatorio_10000.csv",
                "C:/Users/User/Desktop/TDE4/crescente_100.csv",
                "C:/Users/User/Desktop/TDE4/crescente_1000.csv",
                "C:/Users/User/Desktop/TDE4/crescente_10000.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_100.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_1000.csv",
                "C:/Users/User/Desktop/TDE4/decrescente_10000.csv"
        };

        for (String file : files) {
            String dataType = getDataTypeFromFileName(file);
            try {
                int[] data = CSVReader.readCSV(file);

               
                double duration = testSortingAlgorithm(data, algorithm);

                
                updateTableRow(dataType, algorithmName, duration);

            } catch (IOException e) {
                updateTableRow(dataType, algorithmName, -1);
            }
        }
    }

    private static void updateTableRow(String dataType, String algorithmName, double duration) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(dataType)) {
                String formattedTime = (duration >= 0) ? df.format(duration) : "Erro";
                switch (algorithmName) {
                    case "Bubble Sort" -> tableModel.setValueAt(formattedTime, i, 1);
                    case "Insertion Sort" -> tableModel.setValueAt(formattedTime, i, 2);
                    case "Quick Sort" -> tableModel.setValueAt(formattedTime, i, 3);
                }
            }
        }
    }

    private static String getDataTypeFromFileName(String fileName) {
        if (fileName.contains("aleatorio")) return "Aleatório " + fileName.split("_")[1].replace(".csv", "") + " Registros";
        if (fileName.contains("crescente")) return "Crescente " + fileName.split("_")[1].replace(".csv", "") + " Registros";
        if (fileName.contains("decrescente")) return "Decrescente " + fileName.split("_")[1].replace(".csv", "") + " Registros";
        return "Desconhecido";
    }

    @FunctionalInterface
    interface SortingAlgorithm {
        void sort(int[] array);
    }
}
