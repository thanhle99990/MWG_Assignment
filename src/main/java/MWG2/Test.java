/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MWG2;

/**
 *
 * @author Admin
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {

    public static void main(String[] args) {
        try {
            FileInputStream excelFile = new FileInputStream(new File("Test.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            System.out.println(firstCell.getStringCellValue());
            List<Customer> listOfCustomer = new ArrayList<Customer>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(0))));
                customer.setName(currentRow.getCell(1).getStringCellValue());
                customer.setEmail(currentRow.getCell(2).getStringCellValue());
                listOfCustomer.add(customer);
            }
            for (Customer customer : listOfCustomer) {
                System.out.println(customer);
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
