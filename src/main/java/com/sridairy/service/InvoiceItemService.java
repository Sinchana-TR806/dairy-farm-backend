package com.sridairy.erp.service;

import com.sridairy.erp.model.InvoiceItem;
import com.sridairy.erp.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    public List<InvoiceItem> getAllItems() {
        return invoiceItemRepository.findAll();
    }

    public InvoiceItem saveItem(InvoiceItem item) {
        return invoiceItemRepository.save(item);
    }
}
