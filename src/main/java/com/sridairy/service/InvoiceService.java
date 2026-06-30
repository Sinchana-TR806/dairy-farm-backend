package com.sridairy.erp.service;

import com.sridairy.erp.model.Invoice;
import com.sridairy.erp.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
