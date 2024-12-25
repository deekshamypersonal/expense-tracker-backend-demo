package com.expensetracker.expensetracker.bill.service;
//
//
import com.expensetracker.expensetracker.bill.service.classification.ExpenseClassifier;
import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.model.ExpenseRequest;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class BillService {

    private final ExpenseClassifier expenseClassifier;
    private final ExpenseService expenseService;

    public BillService(ExpenseClassifier expenseClassifier, ExpenseService expenseService) {
        this.expenseClassifier = expenseClassifier;
        this.expenseService = expenseService;
    }

    public String processFile(MultipartFile file) {
        try {
            // Check file type (PNG or JPEG)
            String contentType = file.getContentType();
            if (contentType == null ||
                    (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
                return "Invalid file type. Please upload a PNG or JPEG image.";
            }

            ExpenseRequest expenseRequest = new ExpenseRequest();
            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            // Perform OCR
            String text = performOCR(image);

            // Simple heuristic: validate that the OCR'd text looks like a receipt
            if (!isValidReceiptText(text)) {
                return "No valid receipt data found. Please upload a valid bill.";
            }

            // If valid, classify the expense and set details
            expenseClassifier.classifyExpense(text, expenseRequest);
            extractPrice(text, expenseRequest);

            // Save expense
            expenseService.setExpense(expenseRequest);
            return "Expense added successfully. Please refresh to see updates.";

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return "Error processing bill. Expense not added. ABC";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred. Expense not added.";
        }
    }

    private String performOCR(BufferedImage image) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        File tessDataFolder = new ClassPathResource("eng.traineddata").getFile().getParentFile();
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        tesseract.setLanguage("eng");
        return tesseract.doOCR(image).toLowerCase();
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

    /**
     * Basic heuristic checks to validate if the OCR text looks like a receipt.
     * 1) Check text length
     * 2) Check for at least one price symbol
     * 3) Check for keywords like "total" or "tax"
     */
    private boolean isValidReceiptText(String ocrText) {
        // 1) length check
        if (ocrText.length() < 20) {
            return false;
        }

        // 2) price pattern check
        Pattern pricePattern = Pattern.compile("[\\$\\£\\€](\\d+(?:\\.\\d{1,2})?)");
        Matcher priceMatcher = pricePattern.matcher(ocrText);
        if (!priceMatcher.find()) {
            // no price symbol or numeric price found
            return false;
        }

        // 3) optional keywords check
        String lowerText = ocrText.toLowerCase();
        if (!lowerText.contains("total") && !lowerText.contains("tax")) {
            // likely not a valid receipt
            return false;
        }

        return true;
    }
}


