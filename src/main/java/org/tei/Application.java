package org.tei;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        String format = String.format("hello %s %s", "world", "Jack");
        System.out.println("format => " + format);
        String csvFile = "/Users/tatsutei/Documents/test.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                System.out.println("line => " + line);
                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("商品名= " + country[1] + " , 単価=" + country[2] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
