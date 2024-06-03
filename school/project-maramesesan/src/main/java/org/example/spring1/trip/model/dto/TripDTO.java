package org.example.spring1.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.spring1.flight.model.Flight;
import org.example.spring1.flight.model.dto.FlightDTO;
import org.example.spring1.location.model.Location;
import org.example.spring1.location.model.dto.LocationDTO;
import org.example.spring1.room.model.Room;
import org.example.spring1.room.model.dto.RoomDTO;
import org.example.spring1.security.dto.LoginRequest;
import org.example.spring1.security.dto.SignupRequest;
import org.example.spring1.user.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TripDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private LoginRequest user;
    private Set<LocationDTO> locations;
    private Set<FlightDTO> flights;
    private Set<RoomDTO> rooms;
}
