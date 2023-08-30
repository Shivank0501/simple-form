package com.simpleform.controller;

import com.simpleform.model.AdminEnum;
import com.simpleform.model.UsersModel;
import com.simpleform.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService)
    {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getResgisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());

        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());

        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel, @RequestParam String role, @RequestParam String adminCode) {
        System.out.println("register request: " + usersModel);

        AdminEnum selectedRole = AdminEnum.USER; // Default role is USER

        // Check if the admin code matches your predefined admin code
        if ("Shivankisadmin".equals(adminCode)) {
            selectedRole = AdminEnum.ADMIN;
        }

        UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail(), usersModel.getMobile(), usersModel.getGender(), selectedRole);

        return registeredUser == null ? "error_page" : "redirect:/login";
    }


    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Integer userId, HttpSession session) {
       // UsersModel authenticatedUser = (UsersModel) session.getAttribute("authenticatedUser");

      //  if (authenticatedUser.getRole() == AdminEnum.ADMIN) {
            usersService.deleteUser(userId);
            return "redirect:/admin-panel";
       // } else {
            // Handle unauthorized access
       //     return "error_page";
      //  }
    }


    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model) {
        System.out.println("login request: " + usersModel);
        UsersModel authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if (authenticated != null) {
            model.addAttribute("userLogin", authenticated.getLogin());

            if (authenticated.getRole() == AdminEnum.ADMIN) {
                return "redirect:/admin-panel"; // Redirect to admin panel
            } else {
                return "Page"; // Redirect to user's main page
            }
        } else {
            return "error_page";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/forgot")
    public String showForgotPasswordPage() {
        return "forgot";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String login, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        // Verify the input data and reset the password
        if (newPassword.equals(confirmPassword)) {
            UsersModel user = usersService.findByLogin(login);
            if (user != null) {
                usersService.updatePassword(user, newPassword);
                model.addAttribute("successMessage", "Password reset successfully!");
                return "redirect:/login";
            } else {
                model.addAttribute("errorMessage", "User not found.");
            }
        } else {
            model.addAttribute("errorMessage", "Passwords do not match.");
        }
        return "forgot";


    }

    @GetMapping("/edit-profile")
    public String showEditProfilePage(Model model) {
        // Create a new UsersModel instance for editing
        UsersModel userForEdit = new UsersModel();
        model.addAttribute("user", userForEdit);
        return "edit";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UsersModel updatedUser, RedirectAttributes redirectAttributes) {
        UsersModel existingUser = usersService.findByLogin(updatedUser.getLogin());

        if (existingUser != null) {
            existingUser.setLogin(updatedUser.getLogin());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setMobile(updatedUser.getMobile());
            existingUser.setEmail(updatedUser.getEmail());

            usersService.updateUser(existingUser);

            // Add a success message as a flash attribute
            redirectAttributes.addFlashAttribute("successMessage", "Congratulations, your profile has been updated!");
        }

        return "Page"; // Redirect to the main page after updating
    }

    @GetMapping("/admin-panel")
    public String adminPanel(Model model, HttpSession session) {
//        UsersModel authenticatedUser = (UsersModel) session.getAttribute("authenticatedUser");

//        if (authenticatedUser != null && authenticatedUser.getRole() == AdminEnum.ADMIN) {
            List<UsersModel> userList = usersService.getAllUsers();
            model.addAttribute("userList", userList);
            return "admin_panel"; // Return the admin panel view
//        } else {
//            return "redirect:/login"; // Redirect to login if not an admin
//        }
    }





}
