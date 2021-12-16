package ru.snapgot.coolhairstyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.coolhairstyle.model.*;
import ru.snapgot.coolhairstyle.model.Record;
import ru.snapgot.coolhairstyle.model.dto.BarberInfoDto;
import ru.snapgot.coolhairstyle.model.dto.RecordDto;
import ru.snapgot.coolhairstyle.model.dto.UserDto;
import ru.snapgot.coolhairstyle.repos.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/client/")
public class ClientController {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ClientController(UserRepository userRepository,
                            RecordRepository recordRepository,
                            ServiceRepository serviceRepository,
                            CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.serviceRepository = serviceRepository;
        this.commentRepository = commentRepository;
    }

    @PatchMapping("")
    public void ChangeData(@RequestBody User user, Principal principal){
        userRepository.updateDescription(principal.getName(), user.getName(), user.getPhotoUrl(), user.getCity());
    }

    @GetMapping
    public List<UserDto> GetBarbers(Principal principal){
        return userRepository.getNearestBarber(
                userRepository.findByUsername(principal.getName()).getCity(),
                Role.BARBER
        );
    }

    @GetMapping("{id}")
    public BarberInfoDto getBarber(@PathVariable(name = "id") long barberId){
        return new BarberInfoDto(userRepository.getBarber(barberId),
                serviceRepository.findAllByBarber_id(barberId),
                commentRepository.findAllByBarber_id(barberId));
    }

    @PostMapping("{barberId}/{serviceId}/record")
    public void createRecord(@PathVariable(name = "barberId") long barberId,
                             @PathVariable(name = "serviceId") long serviceId,
                             Principal principal){
        recordRepository.save(new Record(
                userRepository.getById(barberId),
                userRepository.findByUsername(principal.getName()),
                serviceRepository.getById(serviceId),
                RecordStatus.NONE
        ));
    }

    @PostMapping("{id}/comment")
    public void createComment(@PathVariable(name = "id") long barberId,
                              @RequestParam(name = "description") String description,
                              Principal principal){
        commentRepository.save(new Comment(
                userRepository.getById(barberId),
                userRepository.findByUsername(principal.getName()),
                description
                )
        );
    }

    @GetMapping("records")
    public List<RecordDto> getRecords(Principal principal){
        return recordRepository.findAllByClientUsername(principal.getName());
    }
}
