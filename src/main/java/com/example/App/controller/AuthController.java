package com.example.App.controller;

import com.example.App.dto.ContactDto;
import com.example.App.entity.Contact;
import com.example.App.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private final ContactService contactService;

    public AuthController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contact", contactDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("contact") ContactDto contactDto,
                               BindingResult bindingResult, Model model) {
        Contact existingEmail = contactService.findByEmail(contactDto.getEmail().toLowerCase());
        Contact existingPhone = contactService.findByPhone(contactDto.getPhone());
        if (existingEmail != null) {
            bindingResult.rejectValue("email", "email error",
                    "This email is already in use");
        }
        if (existingPhone != null) {
            bindingResult.rejectValue("phone", "phone error",
                    "This phone is already in use");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", contactDto);
            return "register";
        }
        contactService.saveContact(contactDto);
        return "redirect:/register?success";
    }

    @GetMapping("/contacts")
    public String listRegisteredContacts(Model model) {
        List<ContactDto> contacts = contactService.findAllContacts();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }
}
