package com.expensetracker.expensetracker.bill.controller;

import com.expensetracker.expensetracker.bill.dto.BillResponse;
import com.expensetracker.expensetracker.bill.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;


    @PostMapping("/upload")
    public BillResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        logger.info("handleFileUpload accessed.");
        return billService.processFile(file);
    }

}
