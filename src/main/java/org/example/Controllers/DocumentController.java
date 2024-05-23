package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Models.Document;
import org.example.dao.DocumentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {
    private final DocumentsDAO documentDAO;

    @Autowired
    public DocumentController(DocumentsDAO documentDAO) {
        this.documentDAO = documentDAO;
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

    @GetMapping("/new")
    public String newDocument(Model model) {
        model.addAttribute("NamesField", documentDAO.getAllFields());
        return "document/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("documentList") @Valid List<Document> documentsList, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "document/new";

        documentDAO.save(documentsList);
        return "redirect:/document";
    }
}
