package ru.snapgot.coolhairstyle.model.dto;

import ru.snapgot.coolhairstyle.model.RecordStatus;

public interface RecordDto {
    long getId();
    String getBarberName();
    String getClientName();
    long getServiceId();
    String getServiceName();
    String getServiceDescription();
    int getServicePrice();
    RecordStatus getRecordStatus();
}
