package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Models.*;
import org.example.dao.DocumentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

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

    ////////////////////////////////////// create new document
    @GetMapping("/newInvoice")
    public String getNewInvoice(@ModelAttribute("invoice") Invoice invoice, Model model) {
        model.addAttribute("isInvoiceExists", model.containsAttribute("invoiceExists"));
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
    public String postNewOrder(@ModelAttribute("orderObj") @Valid Order orderObj, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "document/new/Order";
        if (number != -1 && orderObj.getNumber() != number){
            orderObj.setNumber(this.number);
        }
        orderObj.setOrderDate(LocalDateTime.now());

        documentsList[1] = orderObj;
        return "redirect:/document/newPayment";
    }

    @GetMapping("/newPayment")
    public String getNewPayment(@ModelAttribute("payment") Payment payment) {
        return "document/new/Payment";
    }

    @PostMapping("/createPayment")
    public String postNewPayment(@ModelAttribute("paymentObj") @Valid Payment paymentObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/new/Payment";

        if (number != -1 && paymentObj.getNumber() != number) {
            paymentObj.setNumber(this.number);
        }
        paymentObj.setPaymentDate(LocalDateTime.now());
        documentsList[2] = paymentObj;
        return "redirect:/document/newPaymentInvoice";
    }

    @GetMapping("/newPaymentInvoice")
    public String getNewPaymentInvoice(@ModelAttribute("paymentInvoice") PaymentInvoice paymentInvoice) {
        return "document/new/PaymentInvoice";
    }

    @PostMapping("/createPaymentInvoice")
    public String postNewPaymentInvoice(@ModelAttribute("paymentInvoiceObj") @Valid PaymentInvoice paymentInvoiceObj, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "document/new/PaymentInvoice";

        if (number != -1 && paymentInvoiceObj.getNumber() != number) {
            paymentInvoiceObj.setNumber(this.number);
        }
        paymentInvoiceObj.setPaymentInvoiceDate(LocalDateTime.now());
        documentsList[3] = paymentInvoiceObj;
        documentDAO.save(documentsList);
        return "redirect:/document";
    }

    ////////////////////////////////////// edit
    @GetMapping("/{id}/getEditInvoice")
    public String getEditInvoice(@PathVariable("id") int id, Model model) {
        Invoice invoice = (Invoice) documentDAO.getDocById(id, DocumentType.INVOICE);
        model.addAttribute("invoice", invoice);
        model.addAttribute("isEdit", true);
        model.addAttribute("isInvoiceExists", model.containsAttribute("invoiceExists"));

        return "document/new/Invoice";
    }

    @PostMapping("/{id}/postEditInvoice")
    public String postEditInvoice(@PathVariable("id") int id, @ModelAttribute("invoiceObj") @Valid Invoice invoiceObj, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        int itSelfNumber = documentDAO.getDocById(id, DocumentType.INVOICE).getNumber();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceObj", bindingResult);
            redirectAttributes.addFlashAttribute("invoice", invoiceObj);
            return String.format("redirect:/document/%d/getEditInvoice", id);
        }

        if (documentDAO.existsByNumber(invoiceObj.getNumber()) && invoiceObj.getNumber() != itSelfNumber) {
            redirectAttributes.addFlashAttribute("invoiceExists", invoiceObj.getNumber());
            return String.format("redirect:/document/%d/getEditInvoice", id);
        }

        this.number = invoiceObj.getNumber();
        invoiceObj.setInvoiceDate(LocalDateTime.now());
        System.out.println(invoiceObj.displayInfo());
        documentsList[0] = invoiceObj;
        return String.format("redirect:/document/%d/getEditOrder", id);
    }

    @GetMapping("/{id}/getEditOrder")
    public String getEditOrder(@PathVariable("id") int id, Model model) {
        Order order = (Order) documentDAO.getDocById(id, DocumentType.ORDER);
        model.addAttribute("order", order);
        model.addAttribute("isEdit", true);
        return "document/new/Order";
    }

    @PostMapping("/{id}/postEditOrder")
    public String postEditOrder(@PathVariable("id") int id, @ModelAttribute("orderObj") @Valid Order orderObj, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderObj", bindingResult);
            redirectAttributes.addFlashAttribute("order", orderObj);
            return String.format("redirect:/document/%d/getEditOrder", id);
        }

        if (number != -1 && orderObj.getNumber() != number) {
            orderObj.setNumber(this.number);
        }
        orderObj.setOrderDate(LocalDateTime.now());

        documentsList[1] = orderObj;
        System.out.println(orderObj.displayInfo());
        return String.format("redirect:/document/%d/getEditPayment", id);
    }

    @GetMapping("/{id}/getEditPayment")
    public String getEditPayment(@PathVariable("id") int id, Model model) {
        Payment payment = (Payment) documentDAO.getDocById(id, DocumentType.PAYMENT);
        model.addAttribute("payment", payment);
        model.addAttribute("isEdit", true);
        return "document/new/Payment";
    }

    @PostMapping("/{id}/postEditPayment")
    public String postEditPayment(@PathVariable("id") int id, @ModelAttribute("paymentObj") @Valid Payment paymentObj, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paymentObj", bindingResult);
            redirectAttributes.addFlashAttribute("payment", paymentObj);
            return String.format("redirect:/document/%d/getEditPayment", id);
        }

        if (number != -1 && paymentObj.getNumber() != number) {
            paymentObj.setNumber(this.number);
        }
        paymentObj.setPaymentDate(LocalDateTime.now());
        System.out.println(paymentObj.displayInfo());
        documentsList[2] = paymentObj;
        return String.format("redirect:/document/%d/getEditPaymentInvoice", id);
    }

    @GetMapping("/{id}/getEditPaymentInvoice")
    public String getEditPaymentInvoice(@PathVariable("id") int id, Model model) {
        PaymentInvoice paymentInvoice = (PaymentInvoice) documentDAO.getDocById(id, DocumentType.PAYMENT_INVOICE);
        model.addAttribute("paymentInvoice", paymentInvoice);
        model.addAttribute("isEdit", true);
        return "document/new/PaymentInvoice";
    }


    @PostMapping("/{id}/postEditPaymentInvoice")
    public String postEditPaymentInvoice(@PathVariable("id") int id, @ModelAttribute("paymentInvoiceObj") @Valid PaymentInvoice paymentInvoiceObj, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paymentInvoiceObj", bindingResult);
            redirectAttributes.addFlashAttribute("paymentInvoice", paymentInvoiceObj);
            return String.format("redirect:/document/%d/getEditPaymentInvoice", id);
        }

        if (number != -1 && paymentInvoiceObj.getNumber() != number) {
            paymentInvoiceObj.setNumber(this.number);
        }
        paymentInvoiceObj.setPaymentInvoiceDate(LocalDateTime.now());
        System.out.println(paymentInvoiceObj.displayInfo());
        documentsList[3] = paymentInvoiceObj;
        documentDAO.update(id, documentsList);
        return "redirect:/document";
    }
}
