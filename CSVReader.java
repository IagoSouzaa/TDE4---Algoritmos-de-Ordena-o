import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public static int[] readCSV(String filePath) throws IOException {
        ArrayList<Integer> dataList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

       
        br.readLine();

        while ((line = br.readLine()) != null) {
            try {
               
                int number = Integer.parseInt(line.trim());
                dataList.add(number);
            } catch (NumberFormatException e) {
                
                System.out.println("Linha ignorada (não é um número): " + line);
            }
        }
        br.close();

       
        return dataList.stream().mapToInt(Integer::intValue).toArray();
    }
}
