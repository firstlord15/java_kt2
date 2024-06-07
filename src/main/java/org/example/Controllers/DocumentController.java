package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Models.*;
import org.example.dao.DocumentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/document")
public class DocumentController {
    private final DocumentsDAO documentDAO;
    private final Document[] documentsList;
    private int number;

    @Autowired
    public DocumentController(DocumentsDAO documentDAO) {
        this.documentDAO = documentDAO;
        this.documentsList = new Document[4];
        this.number = -1;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("AllDocuments", documentDAO.index());
        return "document/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("documents", documentDAO.show(id));
        return "document/show";
    }

    @GetMapping("/newInvoice")
    public String newInvoice(@ModelAttribute("invoice") Invoice invoice) {
        return "document/newInvoice";
    }

    @PostMapping("/createInvoice")
    public String invoice(@ModelAttribute("invoiceObj") @Valid Invoice invoiceObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/newInvoice";

        invoiceObj.setInvoiceDate(LocalDateTime.now());
        this.number = invoiceObj.getNumber();
        documentsList[0] = invoiceObj;
        return "redirect:/document/newOrder";
    }

    @GetMapping("/newOrder")
    public String newOrder(@ModelAttribute("order") Order order) {
        return "document/newOrder";
    }

    @PostMapping("/createOrder")
    public String order(@ModelAttribute("orderObj") @Valid Order orderObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/newOrder";

        orderObj.setOrderDate(LocalDateTime.now());
        if (number != -1 && orderObj.getNumber() != number) {
            orderObj.setNumber(this.number);
        }

        documentsList[1] = orderObj;
        return "redirect:/document/newPayment";
    }

    @GetMapping("/newPayment")
    public String newPayment(@ModelAttribute("payment") Payment payment) {
        return "document/newPayment";
    }

    @PostMapping("/createPayment")
    public String payment(@ModelAttribute("paymentObj") @Valid Payment paymentObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/newPayment";

        paymentObj.setPaymentDate(LocalDateTime.now());
        if (number != -1 && paymentObj.getNumber() != number) {
            paymentObj.setNumber(this.number);
        }

        documentsList[2] = paymentObj;
        return "redirect:/document/newPaymentInvoice";
    }

    @GetMapping("/newPaymentInvoice")
    public String newPaymentInvoice(@ModelAttribute("paymentInvoice") PaymentInvoice paymentInvoice) {
        return "document/newPaymentInvoice";
    }

    @PostMapping("/createPaymentInvoice")
    public String paymentInvoice(@ModelAttribute("paymentInvoiceObj") @Valid PaymentInvoice paymentInvoiceObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/newPaymentInvoice";

        paymentInvoiceObj.setPaymentInvoiceDate(LocalDateTime.now());
        if (number != -1 && paymentInvoiceObj.getNumber() != number) {
            paymentInvoiceObj.setNumber(this.number);
        }

        documentsList[3] = paymentInvoiceObj;
        documentDAO.save(documentsList);
        return "redirect:/document";
    }
}
