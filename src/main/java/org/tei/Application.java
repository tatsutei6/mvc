package org.tei;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;


public class Application {
    public static void main(String[] args) throws JsonProcessingException {
        String format = String.format("hello %s %s", "world", "Jack");
        System.out.println("format => " + format);
        parseCSV();
        UserPojo userPojo = new UserPojo();
        userPojo.setAge(20);
        userPojo.setName("jack");
        UserPojo.UserAddress userAddress = new UserPojo.UserAddress();
        userAddress.setCity("Tangshan");
        userAddress.setProvince("Hebei");
        userAddress.setDistrict("north");
        userPojo.setUserAddress(userAddress);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
        System.out.println("mapper.writeValueAsString(userPojo) = " + mapper.writeValueAsString(userPojo));
    }

    private static void parseCSV() {
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
