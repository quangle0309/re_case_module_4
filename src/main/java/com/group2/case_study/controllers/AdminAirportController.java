package com.group2.case_study.controllers;

import com.group2.case_study.models.Airport;
import com.group2.case_study.services.IAdminAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/airports")
public class AdminAirportController {

    @Autowired
    private IAdminAirportService adminAirportService;

    @GetMapping
    public String listAirports(Model model) {
        List<Airport> airports = adminAirportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "admin/airport/airport-management";
    }

    @GetMapping("/new")
    public String createAirportForm(Model model) {
        model.addAttribute("airport", new Airport());
        return "admin/airport/airport-form";
    }

    @PostMapping
    public String saveAirport(@ModelAttribute("airport") Airport airport, RedirectAttributes redirectAttributes) {
        adminAirportService.saveAirport(airport);
        redirectAttributes.addFlashAttribute("message", "Thêm sân bay thành công!");
        return "redirect:/admin/airports";
    }

    @GetMapping("/edit/{id}")
    public String editAirportForm(@PathVariable Integer id, Model model) {
        Airport airport = adminAirportService.getAirportById(id);
        if (airport == null) {
            return "redirect:/admin/airports";
        }
        model.addAttribute("airport", airport);
        return "admin/airport/airport-edit-form";
    }

    @PostMapping("/update/{id}")
    public String updateAirport(@PathVariable Integer id, @ModelAttribute("airport") Airport airport, RedirectAttributes redirectAttributes) {
        airport.setAirportId(id);
        adminAirportService.saveAirport(airport);
        redirectAttributes.addFlashAttribute("message", "Cập nhật sân bay thành công!");
        return "redirect:/admin/airports";
    }

    @GetMapping("/delete/{id}")
    public String deleteAirport(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        adminAirportService.deleteAirport(id);
        redirectAttributes.addFlashAttribute("message", "Xóa sân bay thành công!");
        return "redirect:/admin/airports";
    }

    @GetMapping("/view/{id}")
    public String viewAirport(@PathVariable Integer id, Model model) {
        Airport airport = adminAirportService.getAirportById(id);
        if (airport == null) {
            return "redirect:/admin/airports";
        }
        model.addAttribute("airport", airport);
        return "admin/airport/airport-view";
    }
}
