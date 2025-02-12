//package com.expensetracker.expensetracker.bill.service;
////
////
//import com.expensetracker.expensetracker.bill.controller.BillController;
//import com.expensetracker.expensetracker.bill.service.classification.ExpenseClassifier;
//import com.expensetracker.expensetracker.expenseservice.ExpenseService;
//import com.expensetracker.expensetracker.model.ExpenseRequest;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
////
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//import org.springframework.core.io.ClassPathResource;
//
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//
//@Service
//public class BillService {
//
//    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
//
//    private final ExpenseClassifier expenseClassifier;
//    private final ExpenseService expenseService;
//
//    public BillService(ExpenseClassifier expenseClassifier, ExpenseService expenseService) {
//        this.expenseClassifier = expenseClassifier;
//        this.expenseService = expenseService;
//    }
//
//    public ExpenseRequest processFile(MultipartFile file) {
//        try {
//            // Check file type (PNG or JPEG)
//
//            logger.info("MultipartFile accessed.");
//            String contentType = file.getContentType();
//            if (contentType == null ||
//                    (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
//                return "Invalid file type. Please upload a PNG or JPEG image.";
//            }
//
//            ExpenseRequest expenseRequest = new ExpenseRequest();
//            InputStream inputStream = file.getInputStream();
//            BufferedImage image = ImageIO.read(inputStream);
//
//            // Perform OCR
//            String text = performOCR(image);
//
//            // Simple heuristic: validate that the OCR'd text looks like a receipt
//            if (!isValidReceiptText(text)) {
//                return "No valid receipt data found. Please upload a valid bill.";
//            }
//
//            // If valid, classify the expense and set details
//            expenseClassifier.classifyExpense(text, expenseRequest);
//            extractPrice(text, expenseRequest);
//
//            return expenseRequest;
//
////            // Save expense
////            expenseService.setExpense(expenseRequest);
////            return "Expense added successfully. Please refresh to see updates.";
//
//        } catch (TesseractException e) {
//            e.printStackTrace();
//            return "TesseractException exception occured. ABC";
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return "Error processing bill. Expense not added. ABC";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "An error occurred. Expense not added.";
//        }
//    }
//
//    private String performOCR(BufferedImage image) throws TesseractException, IOException {
//////        Tesseract tesseract = new Tesseract();
//////        File tessDataFolder = new ClassPathResource("eng.traineddata").getFile().getParentFile();
//////        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
//////        tesseract.setLanguage("eng");
//////        return tesseract.doOCR(image).toLowerCase();
////
//////        logger.info("performOCR accessed");
//////
//////        // 1) Locate the resource inside the JAR
//////        ClassPathResource trainedDataResource = new ClassPathResource("eng.traineddata");
//////
//////        if (!trainedDataResource.exists()) {
//////            // Log or throw an error if the file truly isn't in resources
//////            logger.info("eng.traineddata not found in resources!!");
//////            throw new FileNotFoundException("eng.traineddata not found in resources!");
//////        }
//////
//////        // 2) Create a temporary directory for Tesseract’s data
//////        Path tempDir = Files.createTempDirectory("tess_");
//////        File tessDataFile = new File(tempDir.toFile(), "eng.traineddata");
//////
//////        // 3) Copy the .traineddata file out of the JAR into this directory
//////        try (InputStream in = trainedDataResource.getInputStream();
//////             OutputStream out = new FileOutputStream(tessDataFile)) {
//////            in.transferTo(out);
//////        }
//////
//////        // 4) Configure Tesseract to use this temp dir
//////        Tesseract tesseract = new Tesseract();
//////        tesseract.setDatapath(tempDir.toString()); // points to /tmp/... on Heroku
//////        tesseract.setLanguage("eng"); // use eng.traineddata
//////
//////        logger.info("performOCR over");
//////
//////        // 5) Perform OCR
//////        return tesseract.doOCR(image).toLowerCase();
////
////        //---------------------------------------
////
////        logger.info("performOCR accessed");
////
////        // Locate the .traineddata file inside the JAR
////        ClassPathResource trainedDataResource = new ClassPathResource("eng.traineddata");
////
////        if (!trainedDataResource.exists()) {
////            logger.error("eng.traineddata not found in resources!!");
////            throw new FileNotFoundException("eng.traineddata not found in resources!");
////        }
////
////        // Create a temporary directory for Tesseract's data
////        Path tempDir = Files.createTempDirectory("tess_");
////        File tessDataFile = new File(tempDir.toFile(), "eng.traineddata");
////
////        // Copy the .traineddata file out of the JAR into this directory
////        try (InputStream in = trainedDataResource.getInputStream();
////             OutputStream out = new FileOutputStream(tessDataFile)) {
////            in.transferTo(out);
////        }
////
////        System.setProperty("jna.library.path", "/app/.apt/usr/lib/");
////
////        // Configure Tesseract to use this temp directory
////        Tesseract tesseract = new Tesseract();
////        tesseract.setDatapath("/app/.apt/usr/share/tesseract-ocr/4.00/tessdata/");
////       // tesseract.setDatapath(tempDir.toString()); // Set temporary directory as tessdata path
////        tesseract.setLanguage("eng");
////
////        logger.info("Tesseract OCR configuration completed");
////
////        // Perform OCR
////        String result = tesseract.doOCR(image).toLowerCase();
////
////        // Cleanup: Delete temp directory
////        Files.walk(tempDir)
////                .sorted((a, b) -> b.compareTo(a)) // Reverse order to delete files first
////                .map(Path::toFile)
////                .forEach(File::delete);
////
////        logger.info("performOCR completed successfully");
////
////        return result;
//
//        //-----------------------------------------------
//
//        // 1) Load as InputStream
//        ClassPathResource resource = new ClassPathResource("eng.traineddata");
//        InputStream trainedDataStream = resource.getInputStream();
//
//        // 2) Create a temporary directory for Tesseract data
//        File tempTessDataDir = Files.createTempDirectory("tessdata").toFile();
//
//        // 3) Copy the eng.traineddata file into that directory
//        File tessDataFile = new File(tempTessDataDir, "eng.traineddata");
//        try (OutputStream out = new FileOutputStream(tessDataFile)) {
//            trainedDataStream.transferTo(out);
//        }
//
//        // 4) Initialize Tesseract
//        Tesseract tesseract = new Tesseract();
//        // Tesseract expects the file to be in a 'tessdata' subdirectory, or you can set a special config
//        // For simplicity, if your "eng.traineddata" is right in tempTessDataDir, do:
//        tesseract.setDatapath(tempTessDataDir.getAbsolutePath());
//        tesseract.setLanguage("eng");
//
//        // 5) Perform OCR
//        return tesseract.doOCR(image).toLowerCase();
//    }
//
//    private void extractPrice(String text, ExpenseRequest expenseRequest) {
//        Pattern pricePattern = Pattern.compile("[\\$\\£\\€](\\d+(?:\\.\\d{1,2})?)");
//        Matcher priceMatcher = pricePattern.matcher(text);
//        double maxPrice = 0.0;
//        while (priceMatcher.find()) {
//            double price = Double.parseDouble(priceMatcher.group(1));
//            if (price > maxPrice) {
//                maxPrice = price;
//            }
//        }
//        expenseRequest.setAmount(maxPrice);
//    }
//
//    /**
//     * Basic heuristic checks to validate if the OCR text looks like a receipt.
//     * 1) Check text length
//     * 2) Check for at least one price symbol
//     * 3) Check for keywords like "total" or "tax"
//     */
//    private boolean isValidReceiptText(String ocrText) {
//        // 1) length check
//        if (ocrText.length() < 20) {
//            return false;
//        }
//
//        // 2) price pattern check
//        Pattern pricePattern = Pattern.compile("[\\$\\£\\€](\\d+(?:\\.\\d{1,2})?)");
//        Matcher priceMatcher = pricePattern.matcher(ocrText);
//        if (!priceMatcher.find()) {
//            // no price symbol or numeric price found
//            return false;
//        }
//
//        // 3) optional keywords check
//        String lowerText = ocrText.toLowerCase();
//        if (!lowerText.contains("total") && !lowerText.contains("tax")) {
//            // likely not a valid receipt
//            return false;
//        }
//
//        return true;
//    }
//}
//
//

//--------------------------------upload bill

package com.expensetracker.expensetracker.bill.service;

import com.expensetracker.expensetracker.bill.dto.BillResponse;
import com.expensetracker.expensetracker.bill.service.classification.ExpenseClassifier;
import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.model.ExpenseRequest;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    private final ExpenseClassifier expenseClassifier;
    private final ExpenseService expenseService;

    public BillService(ExpenseClassifier expenseClassifier, ExpenseService expenseService) {
        this.expenseClassifier = expenseClassifier;
        this.expenseService = expenseService;
    }

    public BillResponse processFile(MultipartFile file) {
        BillResponse response = new BillResponse();
        try {
            // Check file type (PNG or JPEG)
            String contentType = file.getContentType();
            if (contentType == null ||
                    (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
                response.setSuccess(false);
                response.setMessage("Invalid file type. Please upload a PNG or JPEG image.");
                return response;
            }

            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            // Perform OCR
            String text = performOCR(image);

            // Validate the OCR text
            if (!isValidReceiptText(text)) {
                response.setSuccess(false);
                response.setMessage("No valid receipt data found. Please upload a valid bill.");
                return response;
            }

            // If valid, classify the expense
            ExpenseRequest expenseRequest = new ExpenseRequest();
            expenseClassifier.classifyExpense(text, expenseRequest);
            extractPrice(text, expenseRequest);

            // Return success with the parsed request
            response.setSuccess(true);
            response.setMessage("Bill parsed successfully.");
            response.setExpenseRequest(expenseRequest);
            return response;

        } catch (TesseractException e) {
            logger.error("TesseractException: " + e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage("Error reading the image (OCR failed).");
            return response;
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage("Error processing image file.");
            return response;
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage("An unexpected error occurred. Please try again.");
            return response;
        }
    }

    private String performOCR(BufferedImage image) throws TesseractException, IOException {
        // Copy eng.traineddata from resources into a temporary folder for Tesseract
        ClassPathResource resource = new ClassPathResource("eng.traineddata");
        InputStream trainedDataStream = resource.getInputStream();

        File tempTessDataDir = Files.createTempDirectory("tessdata").toFile();
        File tessDataFile = new File(tempTessDataDir, "eng.traineddata");
        try (OutputStream out = new FileOutputStream(tessDataFile)) {
            trainedDataStream.transferTo(out);
        }

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tempTessDataDir.getAbsolutePath());
        tesseract.setLanguage("eng");

        // Perform OCR
        String result = tesseract.doOCR(image).toLowerCase();

        // Clean up the temporary directory
        Files.walk(tempTessDataDir.toPath())
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(File::delete);

        return result;
    }

    private boolean isValidReceiptText(String ocrText) {
        // 1) length check
        if (ocrText.length() < 20) {
            return false;
        }
        // 2) check for at least one price symbol
        Pattern pricePattern = Pattern.compile("[\\$\\£\\€](\\d+(?:\\.\\d{1,2})?)");
        Matcher priceMatcher = pricePattern.matcher(ocrText);
        if (!priceMatcher.find()) {
            // no price symbol or numeric price found
            return false;
        }
        // 3) optional keywords check
        String lowerText = ocrText.toLowerCase();
        if (!lowerText.contains("total") && !lowerText.contains("tax")) {
            return false;
        }
        return true;
    }

    private void extractPrice(String text, ExpenseRequest expenseRequest) {
        Pattern pricePattern = Pattern.compile("[\\$\\£\\€](\\d+(?:\\.\\d{1,2})?)");
        Matcher priceMatcher = pricePattern.matcher(text);
        double maxPrice = 0.0;
        while (priceMatcher.find()) {
            double price = Double.parseDouble(priceMatcher.group(1));
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        expenseRequest.setAmount(maxPrice);
    }
}

