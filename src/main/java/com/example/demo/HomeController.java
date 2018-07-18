package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//ch 19
//ch 20
//ch 21 im too busy miss anything, clean feedback w
//ch21 and bullhorn

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }


    //
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        model.addAttribute("user", user);

        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully created");
        }
        return "index";
    }

    //!
//    @RequestMapping("/")
//    public String index() {
//        return "index";
////        return "listmsg";
//    }

    @RequestMapping("/")
    public String index(Model model) {
//        return "index";
        model.addAttribute("messages", messageRepository.findAll());
        return "listmsg";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/secure")
    public String secure() {
        return "secure";
    }


    // //
    @RequestMapping("/listmsg")
    public String listmsg(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "listmsg";
    }

    //    @RequestMapping("/")
//    public String listMessages(Model model){
//        model.addAttribute("messages", messageRepository.findAll());
//        return "listmsg";
//    }

//    @RequestMapping("/msgform")
//    public String msgform() {
//        return "msgform";
//    }

    @RequestMapping("/logout")
    public String logout() {
        return "/";
    }

    //

    @Autowired
    MessageRepository messageRepository;

//    @RequestMapping("/")
//    public String listMessages(Model model){
//        model.addAttribute("messages", messageRepository.findAll());
//        return "listmsg";
//    }


    @GetMapping("/add")
    public String messageForm(Model model){
        model.addAttribute("message", new Message());
        return "msgform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message, BindingResult result){
        if(result.hasErrors()){
            return "msgform";
        }

        messageRepository.save(message);
//        return "redirect:/";
        return "listmsg";
    }

    @RequestMapping("/detail/{id}")
    public String showMsg(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMsg(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "msgform";
    }


    @RequestMapping("/delete/{id}")
    public String deleteMsg (@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
//        return "listmsg";
    }
}
