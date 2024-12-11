package com.expensetracker.expensetracker.bill.controller;

import com.expensetracker.expensetracker.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return billService.processFile(file);
    }

}
