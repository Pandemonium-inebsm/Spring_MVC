package com.example.spring_mvc.web;

import com.example.spring_mvc.entities.Patient;
import com.example.spring_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping(path = "/index")
    public String patients (Model model,
                            @RequestParam(name = "page", defaultValue = "0")int page,
                            @RequestParam(name = "size", defaultValue = "5")int size,
                            @RequestParam(name = "keyword", defaultValue = "")String keyword){

        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";

    }

    //approche rendu coté serveur
    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    //approche rendu coté client
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> lisPatients(){
        return patientRepository.findAll();
    }

}
