package com.final_project.daily_operations.mapper.mapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class CurrencyRateXMLMapper {

    public void mapToObj(String xmlString) throws JAXBException {

        Scanner scanner = new Scanner(xmlString);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
           if (line.contains("<Ccy>") && !line.contains("EUR")){
               String newString = line.replace("<Ccy>","").replace("</Ccy>","");
               System.out.println(newString);
           }
        } //TODO pabaigti su key value
        scanner.close();


    }
}
