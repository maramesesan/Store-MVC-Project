package org.example.spring1.room.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.spring1.accommodation.model.Accommodation;
import org.example.spring1.accommodation.model.dto.AccommodationDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private float price;
    private int numberPeople;
    private String details;
    private AccommodationDTO accommodation;
}
