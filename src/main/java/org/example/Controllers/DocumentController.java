package org.example.Controllers;

import org.example.Models.Document;
import org.example.dao.DocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {
    private final DocumentDAO documentDAO;

    @Autowired
    public DocumentController(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("Alldocuments", documentDAO.index());
        return "document/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("documents", documentDAO.show(id));
        return "document/show";
    }

    @GetMapping("/new")
    public String newDocument(@ModelAttribute("documents") List<Document> documents) {
        return "document/new";
    }
}
