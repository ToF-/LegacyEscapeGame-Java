package rent;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Rent {
    public static void main(String[] args) throws IOException {
        List<Order> orders;
        orders = new ArrayList<Order>();
        String fileName = System.getenv("LAGS_ORDER_FILE");
        if (fileName == null) {
            System.err.println("wich file ? set LAGS_ORDER_FILE var");
            exit(1);
        }
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            CSVIterator iterator = new CSVIterator(reader);
            boolean isFirstLine = true;
            for (CSVIterator it = iterator; it.hasNext(); ) {
                String[] line = it.next();
                if(! isFirstLine) {
                    String idt = line[0].trim();
                    int start = Integer.parseInt(line[1].trim());
                    int durn = Integer.parseInt((line[2].trim()));
                    int bid = Integer.parseInt(line[3].trim());
                    Order o = new Order(idt, start, durn, bid);
                    orders.add(o);
                }
                isFirstLine = false;
            }
        } catch (IOException e) {
            System.err.println("problem reading file " + fileName );
            exit(1);
        } catch (CsvValidationException e) {
            System.err.println("problem reading file " + fileName );
            exit(1);
        }
        Lags lags = new Lags(orders);
        lags.revenue();
        exit(0);
    }

}
