package org.example.spring1.trip;


import org.example.spring1.trip.model.Trip;
import org.example.spring1.trip.model.dto.TripCreationDTO;
import org.example.spring1.trip.model.dto.TripDTO;
import org.example.spring1.trip.model.dto.TripLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripMapper {
    TripDTO toTripDto(Trip trip);

    Trip toEntity(TripDTO trip);
    Trip toEntityFromCreation(TripCreationDTO trip);

    TripCreationDTO toTripCreationDto(Trip trip);

    TripLocationDTO toTripLocationDto(Trip trip);
}
