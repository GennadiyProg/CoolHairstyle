package ru.snapgot.coolhairstyle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BarberInfoDto {
    private UserDto barberInfo;
    private List<ServiceDto> services;
    private List<CommentDto> comments;
}
