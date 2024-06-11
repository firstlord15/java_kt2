package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Models.*;
import org.example.dao.DocumentsDAO;
import org.example.dao.InvoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/document")
public class DocumentController {
    private final DocumentsDAO documentDAO;
    private final Document[] documentsList;
    private int number;

    @Autowired
    public DocumentController(DocumentsDAO documentDAO, InvoiceDAO invoiceDAO) {
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

    ////////////////////////////////////// create new document
    @GetMapping("/newInvoice")
    public String getNewInvoice(@ModelAttribute("invoice") Invoice invoice, Model model) {
        if (model.containsAttribute("invoiceExists")) {
            model.addAttribute("isInvoiceExists", true);
        } else {
            model.addAttribute("isInvoiceExists", false);
        }
        return "document/new/Invoice";
    }

    @PostMapping("/createInvoice")
    public String postNewInvoice(@ModelAttribute("invoiceObj") @Valid Invoice invoiceObj, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "document/new/Invoice";
        }

        invoiceObj.setInvoiceDate(LocalDateTime.now());
        this.number = invoiceObj.getNumber();

        if (documentDAO.existsByNumber(number)) {
            redirectAttributes.addFlashAttribute("invoiceExists", this.number);
            return "redirect:/document/newInvoice";
        }

        documentsList[0] = invoiceObj;
        return "redirect:/document/newOrder";
    }

    @GetMapping("/newOrder")
    public String getNewOrder(@ModelAttribute("order") Order order) {
        return "document/new/Order";
    }

    @PostMapping("/createOrder")
    public String postEditOrder(@ModelAttribute("orderObj") @Valid Order orderObj, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "document/new/Order";

        orderObj.setOrderDate(LocalDateTime.now());
        if (number != -1 && orderObj.getNumber() != number)
            orderObj.setNumber(this.number);

        documentsList[1] = orderObj;
        return "redirect:/document/newPayment";
    }

    @GetMapping("/newPayment")
    public String getNewPayment(@ModelAttribute("payment") Payment payment) {
        return "document/new/Payment";
    }

    @PostMapping("/createPayment")
    public String postEditPayment(@ModelAttribute("paymentObj") @Valid Payment paymentObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/new/Payment";

        paymentObj.setPaymentDate(LocalDateTime.now());
        if (number != -1 && paymentObj.getNumber() != number) {
            paymentObj.setNumber(this.number);
        }

        documentsList[2] = paymentObj;
        return "redirect:/document/newPaymentInvoice";
    }

    @GetMapping("/newPaymentInvoice")
    public String getNewPaymentInvoice(@ModelAttribute("paymentInvoice") PaymentInvoice paymentInvoice) {
        return "document/new/PaymentInvoice";
    }

    @PostMapping("/createPaymentInvoice")
    public String postEditPaymentInvoice(@ModelAttribute("paymentInvoiceObj") @Valid PaymentInvoice paymentInvoiceObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/new/PaymentInvoice";

        paymentInvoiceObj.setPaymentInvoiceDate(LocalDateTime.now());
        if (number != -1 && paymentInvoiceObj.getNumber() != number) {
            paymentInvoiceObj.setNumber(this.number);
        }

        documentsList[3] = paymentInvoiceObj;
        documentDAO.save(documentsList);
        return "redirect:/document";
    }

    ////////////////////////////////////// edit
    @GetMapping("/{id}/editInvoice")
    public String getEditInvoice(@PathVariable("id") int id, @ModelAttribute("invoice") Invoice invoice, Model model) {
        if (model.containsAttribute("invoiceExists")) {
            model.addAttribute("isInvoiceExists", true);
        } else {
            model.addAttribute("isInvoiceExists", false);
        }
        return "document/new/Invoice";
    }

    @PostMapping("/{id}/createInvoice")
    public String postEditInvoice(@PathVariable("id") int id, Invoice invoice, RedirectAttributes redirectAttributes) {
        Invoice invoiceObj = (Invoice) documentDAO.getDocById(id, DocumentType.INVOICE);
        this.number = invoice.getNumber();

        if (documentDAO.existsByNumber(number)) {
            redirectAttributes.addFlashAttribute("invoiceExists", this.number);
            return "redirect:/document/newInvoice";
        }

        documentsList[0] = invoice;
        return "redirect:/document/newOrder";
    }
}
