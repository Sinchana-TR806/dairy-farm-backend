package com.sridairy.erp.controller;

import com.sridairy.erp.model.InvoiceItem;
import com.sridairy.erp.service.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
@CrossOrigin(origins = "https://dairyfarm-management.netlify.app")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @GetMapping
    public List<InvoiceItem> getAllItems() {
        return invoiceItemService.getAllItems();
    }

    @PostMapping
    public InvoiceItem saveItem(@RequestBody InvoiceItem item) {
        return invoiceItemService.saveItem(item);
    }
}
