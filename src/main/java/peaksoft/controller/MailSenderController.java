package peaksoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.model.MailSender;
import peaksoft.service.impl.ApplicationService;
import peaksoft.service.impl.MailSenderService;

import java.util.List;
@Controller
@RequestMapping("/mailing")
public class MailSenderController {
    private final ApplicationService applicationService;
    private final MailSenderService mailSenderService;

    public MailSenderController(ApplicationService applicationService, MailSenderService mailSenderService) {
        this.applicationService = applicationService;
        this.mailSenderService = mailSenderService;
    }
    @GetMapping()
         public String addMailSender(Model model){
            model.addAttribute("mailing", new MailSender());
            return "mailSender/save";
}
@PostMapping("/send")
public String sendMailing(@ModelAttribute("mailSender")MailSender mailSender){
        mailSenderService.save(mailSender);
        return "redirect:/mailing/find-all";
}@GetMapping("find-all")
public String findAll(Model model){
        model.addAttribute("MailSender",mailSenderService.findAll());
        return "mailSender/get-all";
}
    @GetMapping("/find-all")
    public String getMailSender(Model model) {
        model.addAttribute("mailSender", mailSenderService.findAll());
        return "mailSender/get-all";
    }

    @GetMapping("/update/{id}")
    public String uppDate(@PathVariable("id") Long id, Model model) {
        MailSender mailSender = mailSenderService.findById(id);
        model.addAttribute("app", mailSender);
        return "mailSender/update";
    }

    @PostMapping("{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("mailSender") MailSender mailSender) {
        mailSenderService.update(id, mailSender);
        return "redirect:find-all";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        mailSenderService.deleteById(id);
        return "redirect:find-all";

    }

    @ModelAttribute("mailSender")
    public List<MailSender> getMaiSender() {
        return mailSenderService.findAll();
    }


}
