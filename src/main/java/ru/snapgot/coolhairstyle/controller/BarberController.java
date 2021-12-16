package ru.snapgot.coolhairstyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.coolhairstyle.model.*;
import ru.snapgot.coolhairstyle.model.Record;
import ru.snapgot.coolhairstyle.model.dto.CommentDto;
import ru.snapgot.coolhairstyle.model.dto.RecordDto;
import ru.snapgot.coolhairstyle.model.dto.ServiceDto;
import ru.snapgot.coolhairstyle.repos.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/barber/")
public class BarberController {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BarberController(UserRepository userRepository,
                            RecordRepository recordRepository,
                            ServiceRepository serviceRepository,
                            CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.serviceRepository = serviceRepository;
        this.commentRepository = commentRepository;
    }

    @PatchMapping
    public void ChangeData(@RequestBody User user, Principal principal){
        userRepository.updateDescription(principal.getName(), user.getName(), user.getPhotoUrl(), user.getCity());
    }

    @PostMapping("service")
    public void createService(@RequestBody Service service, Principal principal){
        service.setBarber(userRepository.findByUsername(principal.getName()));
        serviceRepository.save(service);
    }

    @GetMapping("service")
    public List<ServiceDto> getServices(Principal principal){
        return serviceRepository.findAllByBarber(principal.getName());
    }

    @GetMapping("comment")
    public List<CommentDto> getComments(Principal principal){
        return commentRepository.findAllByBarberUsername(principal.getName());
    }

    @GetMapping("record")
    public List<RecordDto> getRecords(Principal principal){
        return recordRepository.findAllByBarberUsername(principal.getName());
    }

    @PatchMapping("record/{id}")
    public void acceptRecord(@PathVariable(name = "id") long recordId, @RequestParam(name = "isAccept") boolean isAccept){
        recordRepository.updateAccept(recordId, isAccept ? RecordStatus.ACCEPTED : RecordStatus.REJECTED);
    }
}
