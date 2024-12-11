package com.expensetracker.expensetracker.bill.service;


import com.expensetracker.expensetracker.bill.service.classification.ExpenseClassifier;
import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.model.ExpenseRequest;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            ExpenseRequest expenseRequest = new ExpenseRequest();
            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            String text = performOCR(image);

            // Classify the expense and populate expenseRequest with category and description
            expenseClassifier.classifyExpense(text, expenseRequest);

            // Extract price and set it to expenseRequest
            extractPrice(text, expenseRequest);

            // Now that expenseRequest is fully populated, save it
            expenseService.setExpense(expenseRequest);

            return "Expense added successfully";
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return "Error processing bill. Expense not added.";
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
        Pattern datePattern = Pattern.compile("\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}");
        Matcher dateMatcher = datePattern.matcher(text);
        StringBuilder dates = new StringBuilder();
        while (dateMatcher.find()) {
            dates.append(dateMatcher.group()).append(" ");
        }

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

