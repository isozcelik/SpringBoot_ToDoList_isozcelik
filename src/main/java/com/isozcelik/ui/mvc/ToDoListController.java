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
            repository.save(ToDoListEntity.builder().note("not: " + i).status("In Progress").build());
        }
        return i + " adet not kaydedildi";
    }

    //Listeleme
    //http://localhost:8080/todolist
    @GetMapping("todolist")
    public String getToDoList(Model model) {
        List<ToDoListEntity> list = repository.findAll();
        model.addAttribute("list_key", list);
        model.addAttribute("save_key", new ToDoListDto()); // buradaki gibi
        return "todolist";
    }
/*  Tek bir html sayfası için aynı url'e sahip 2 adet getmapping verilemez. Onun yerine tek getmapping içerisinde 2 adet addAttribute verilebilir.
    //Save listelemenin olduğu sayfada add yapamadım.
    //GetMapping
    //http://localhost:8080/todolist
    @GetMapping("todolist")
    public String getForm(Model model) {
        model.addAttribute("save_key", new ToDoListDto());
        return "todolist";
    }
*/
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
        toDoListEntity.setStatus("In Progress");
        repository.save(toDoListEntity);
        return "redirect:/todolist";
    }

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

    //Update Get
    //http://localhost:8080/update/todolist/1
    @GetMapping("update/todolist/{id}")
    public String updateToDoListGetForm(@PathVariable(name = "id") Long id, Model model) {
        Optional<ToDoListEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            model.addAttribute("update_key", optionalEntity.get());
        } else {
            model.addAttribute("update_key", "failed");
        }
        return "todolist_update";
    }

    //Update Post
    //http://localhost:8080/update/todolist/1
    @PostMapping("update/todolist/{id}")
    public String updateToDoListPostForm(@Valid @ModelAttribute("update_key") ToDoListDto toDoListDto, @PathVariable(name="id")Long id,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todolist_update";
        }
        Optional<ToDoListEntity> optionalEntity =repository.findById(id);
        if (optionalEntity.isPresent()){
            ToDoListEntity entity =optionalEntity.get();
            entity.setNote(toDoListDto.getNote());
            repository.save(entity);
        }
        return "redirect:/todolist";
    }
}
