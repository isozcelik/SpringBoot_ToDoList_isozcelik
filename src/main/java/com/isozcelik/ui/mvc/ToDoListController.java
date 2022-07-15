package com.isozcelik.ui.mvc;

import com.isozcelik.business.dto.ToDoListDto;
import com.isozcelik.data.entity.ToDoListEntity;
import com.isozcelik.data.repository.IToDoListRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class ToDoListController {
    @Autowired
    IToDoListRepository repository;

    //Fake Save
    //http://localhost:8080/todolist/fakesave
    @GetMapping("todolist/fakesave")
    @ResponseBody
    public String saveToDoList() {
        int i = 0;
        for (i = 0; i < 10; i++) {
            repository.save(ToDoListEntity.builder().note("not: " + i).build());
        }
        return i + " adet not kaydedildi";
    }

    //Listeleme
    //http://localhost:8080/todolist
    @GetMapping("todolist")
    public String getToDoList(Model model) {
        List<ToDoListEntity> list = repository.findAll();
        model.addAttribute("list_key", list);
        return "todolist";
    }
/*
    //Save listelemenin olduğu sayfada add yapamadım.
    //GetMapping
    //http://localhost:8080/todolist
    @GetMapping("todolist")
    public String getForm(Model model) {
        model.addAttribute("save_key", new ToDoListDto());
        return "todolist";
    }

    //Save
    //PostMapping
    //http://localhost:8080/todolist
    @PostMapping("todolist")
    public String postForm(@Valid @ModelAttribute("save_key") ToDoListDto toDoListDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Hata Oluştu.");
            return "todolist";
        }
        ToDoListEntity toDoListEntity = new ToDoListEntity();
        toDoListEntity.setNote(toDoListDto.getNote());
        repository.save(toDoListEntity);
        return "redirect:/todolist";
    }
    */

    //Delete
    //http://localhost:8080/delete/todolist/1
    @GetMapping("delete/todolist/{id}")
    public String deleteToDoListById(@PathVariable(name = "id") Long id, Model model) {
        Optional<ToDoListEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            model.addAttribute("delete_key", optionalEntity.get());
            repository.deleteById(id);
        } else {
            model.addAttribute("failed", "Silinmedi");
        }
        return "redirect:/todolist";
    }
}
