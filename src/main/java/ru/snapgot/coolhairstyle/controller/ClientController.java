package ru.snapgot.coolhairstyle.controller;

import org.springframework.web.bind.annotation.*;
import ru.snapgot.coolhairstyle.model.Barber;
import ru.snapgot.coolhairstyle.model.Comment;

import java.util.List;

@RestController
@RequestMapping("/client/")
public class ClientController {
    @PatchMapping("{id}")
    public void ChangeData(@PathVariable long id,
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam(name = "photo", required = false) String photoUrl,
                           @RequestParam(name = "city", required = false) String city){

    }

    @GetMapping
    public List<Barber> GetBarbers(){
        return null;
    }

    @GetMapping("{id}")
    public Barber getBarber(@PathVariable long barberId){
        return null;
    }

    @PostMapping("{id}/record")
    public void createRecord(@PathVariable long barberId){

    }

    @PostMapping("{id}/comment")
    public void createComment(@PathVariable long barberId){

    }

    @GetMapping("comments")
    public List<Comment> getComments(){
        return null;
    }
}
