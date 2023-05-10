package com.test.outputfileVerification;
import org.testng.annotations.Test;

import io.cucumber.java.en.Given;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class outputfileVerificationTest {
	


	    @Test
	    public void testExcelFiles() throws IOException {
	        
	        
	    	String projectPath = System.getProperty("user.dir");

	    	

	    	

	    	    
	    	        try {
	    	            // Load the instrument, position, and output files
	    	            FileInputStream instrumentFile = new FileInputStream(new File(projectPath+"/src/test/resources/instrumentFile.xlsx"));
	    	            FileInputStream positionFile = new FileInputStream(new File(projectPath+"/src/test/resources/positionFile.xlsx"));
	    	            FileInputStream outputFile = new FileInputStream(new File(projectPath+"/src/test/resources/outputFile.xlsx"));

	    	            // Create workbook instances for each file
	    	            Workbook instrumentWorkbook = new XSSFWorkbook(instrumentFile);
	    	            Workbook positionWorkbook = new XSSFWorkbook(positionFile);
	    	            Workbook outputWorkbook = new XSSFWorkbook(outputFile);

	    	            // Get the first sheet from each workbook
	    	            Sheet instrumentSheet = instrumentWorkbook.getSheetAt(0);
	    	            Sheet positionSheet = positionWorkbook.getSheetAt(0);
	    	            Sheet outputSheet = outputWorkbook.getSheetAt(0);

	    	            // Scenario 1: Check if Position id, ISIN, and Quantity in the output file match with data in the position file
	    	            for (Row outputRow : outputSheet) {
	    	                String outputPositionId = outputRow.getCell(1).getStringCellValue();
	    	                String outputISIN = outputRow.getCell(2).getStringCellValue();
	    	                double outputQuantity;

	    	                try {
	    	                    outputQuantity = outputRow.getCell(3).getNumericCellValue();
	    	                } catch (IllegalStateException e) {
	    	                    // Handle cells that cannot be parsed as doubles
	    	                    continue;
	    	                }

	    	                for (Row positionRow : positionSheet) {
	    	                    String positionId = positionRow.getCell(0).getStringCellValue();
	    	                    String positionISIN = positionRow.getCell(1).getStringCellValue();
	    	                    double positionQuantity;

	    	                    try {
	    	                        positionQuantity = positionRow.getCell(2).getNumericCellValue();
	    	                    } catch (IllegalStateException e) {
	    	                        // Handle cells that cannot be parsed as doubles
	    	                        continue;
	    	                    }

	    	                    if (outputPositionId.equals(positionId) && outputISIN.equals(positionISIN) && outputQuantity == positionQuantity) {
	    	                        System.out.println("Match found for scenario 1 ");
	    	                        break;
	    	                    }
	    	                }
	    	            }


	    	            // Scenario 2: Find the corresponding Instrument id in the instrument file based on Instrument id in the position file and get the unit price
	    	            for (Row positionRow : positionSheet) {
	    	                String instrumentId = positionRow.getCell(1).getStringCellValue();
	    	                double quantity;

	    	                try {
	    	                    quantity = positionRow.getCell(2).getNumericCellValue();
	    	                } catch (IllegalStateException e) {
	    	                    // Handle cells that cannot be parsed as doubles
	    	                    continue;
	    	                }

	    	                for (Row instrumentRow : instrumentSheet) {
	    	                    String id = instrumentRow.getCell(0).getStringCellValue();
	    	                    Cell unitPriceCell = instrumentRow.getCell(3);

	    	                    double unitPrice;

	    	                    if (unitPriceCell.getCellType() == CellType.NUMERIC) {
	    	                        unitPrice = unitPriceCell.getNumericCellValue();
	    	                    } else if (unitPriceCell.getCellType() == CellType.STRING) {
	    	                        String unitPriceString = unitPriceCell.getStringCellValue();
	    	                        if (!unitPriceString.isEmpty() && unitPriceString.matches("-?\\d+(\\.\\d+)?")) {
	    	                            unitPrice = Double.parseDouble(unitPriceString);
	    	                        } else {
	    	                            // Skip the row if the cell value is not numeric
	    	                            continue;
	    	                        }
	    	                    } else {
	    	                        // Skip the row for other cell types
	    	                        continue;
	    	                    }

	    	                    if (instrumentId.equals(id)) {
	    	                        double totalPrice = unitPrice * quantity;
	    	                        System.out.println("Total price is: " + totalPrice);
	    	                        break;
	    	                    }
	    	                }

	    	            }



	    	            // Scenario 3: Verify if the total price in the output file matches with the values calculated in Scenario 2
	    	            for (Row outputRow : outputSheet) {
	    	                Cell totalPriceCell = outputRow.getCell(4);
	    	                double outputTotalPrice;

	    	                if (totalPriceCell == null) {
	    	                    // Handle null cell
	    	                    continue;
	    	                }

	    	                if (totalPriceCell.getCellType() == CellType.NUMERIC) {
	    	                    outputTotalPrice = totalPriceCell.getNumericCellValue();
	    	                } else if (totalPriceCell.getCellType() == CellType.STRING) {
	    	                    String totalPriceString = totalPriceCell.getStringCellValue();
	    	                    if (!totalPriceString.isEmpty() && totalPriceString.matches("-?\\d+(\\.\\d+)?")) {
	    	                        outputTotalPrice = Double.parseDouble(totalPriceString);
	    	                    } else {
	    	                        // Skip the row if the cell value is not numeric
	    	                        continue;
	    	                    }
	    	                } else {
	    	                    // Skip the row for other cell types
	    	                    continue;
	    	                }

	    	                Cell quantityCell = outputRow.getCell(3);
	    	                Cell unitPriceCell = outputRow.getCell(2);

	    	                if (quantityCell == null || unitPriceCell == null) {
	    	                    // Handle null cells
	    	                    continue;
	    	                }

	    	                double quantity;
	    	                double unitPrice;

	    	                if (quantityCell.getCellType() == CellType.NUMERIC) {
	    	                    quantity = quantityCell.getNumericCellValue();
	    	                } else {
	    	                    // Skip the row if the quantity cell is not numeric
	    	                    continue;
	    	                }

	    	                if (unitPriceCell.getCellType() == CellType.NUMERIC) {
	    	                    unitPrice = unitPriceCell.getNumericCellValue();
	    	                } else if (unitPriceCell.getCellType() == CellType.STRING) {
	    	                    String unitPriceString = unitPriceCell.getStringCellValue();
	    	                    if (!unitPriceString.isEmpty() && unitPriceString.matches("-?\\d+(\\.\\d+)?")) {
	    	                        unitPrice = Double.parseDouble(unitPriceString);
	    	                    } else {
	    	                        // Skip the row if the unit price cell value is not numeric
	    	                        continue;
	    	                    }
	    	                } else {
	    	                    // Skip the row for other cell types
	    	                    continue;
	    	                }

	    	                double calculatedTotalPrice = unitPrice * quantity;
	    	                if (Double.compare(outputTotalPrice, calculatedTotalPrice) == 0) {
	    	                    System.out.println("Total price matched");
	    	                } else {
	    	                	System.out.println("Total price NOT matched");
	    	                }
	    	            }




	    	            // Close the workbook instances and file streams
	    	            instrumentWorkbook.close();
	    	            positionWorkbook.close();
	    	            outputWorkbook.close();
	    	            instrumentFile.close();
	    	            positionFile.close();
	    	            outputFile.close();
	    	        } catch (IOException e) {
	    	            e.printStackTrace();
	    	        }
	    	    
	    	

	    	
	         
	    
}
	        
}	       

  

    	 

