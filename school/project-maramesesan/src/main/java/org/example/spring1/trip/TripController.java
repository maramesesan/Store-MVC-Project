package org.example.spring1.trip;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.example.spring1.exception.EntityNotFoundException;
import org.example.spring1.flight.model.Flight;
import org.example.spring1.flight.model.dto.FlightDTO;
import org.example.spring1.global.SingleBodyRequestDTO;
import org.example.spring1.location.model.dto.LocationDTO;
import org.example.spring1.trip.model.Trip;
import org.example.spring1.trip.model.dto.TripCreationDTO;
import org.example.spring1.trip.model.dto.TripCreationRequest;
import org.example.spring1.trip.model.dto.TripDTO;
import org.example.spring1.trip.model.dto.TripLocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.spring1.UrlMapping.*;

@Controller
@RequestMapping(TRIP)
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
//    // @GetMapping
//    public List<TripDTO> findAll() {
//        return tripService.findAll();
//    }
//
//
//    // @GetMapping(ID_PART)
//    public ResponseEntity<?> get(@PathVariable Long id) {
//        return tripService.get(id);
//    }

//    @PostMapping()
//    public ResponseEntity<?> create(
//            @RequestParam Long flightId,
//            @RequestParam Long locationId,
//            @RequestParam Long accommodationId,
//            @RequestBody TripCreationDTO request) {
//        try {
//            TripCreationDTO trip = tripService.create(request, locationId, flightId, accommodationId);
//            return new ResponseEntity<>(trip, HttpStatus.CREATED);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (ParseException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping
    public ResponseEntity<TripCreationDTO> createTrip(@RequestBody TripCreationRequest tripCreationRequest) {
        try {
            TripCreationDTO createdTrip = tripService.create(
                    tripCreationRequest.getDto(),
                    tripCreationRequest.getLocationId(),
                    tripCreationRequest.getRoomId(),
                    tripCreationRequest.getFlightIds()
            );
            return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTripById(@PathVariable Long id) {
        return tripService.get(id);
    }


    @DeleteMapping(ID_PART)
    public void delete(@PathVariable Long id) {
        tripService.delete(id);
    }

    @DeleteMapping
    public void deleteMultiple(@RequestParam List<Long> ids) {
        tripService.deleteMultiple(ids);
    }

//    @PutMapping(ID_PART)
//    public TripDTO update(@PathVariable Long id, @RequestBody TripDTO dto) {
//        return tripService.update(id, dto);
//    }


    @PatchMapping("/{id}/changeFlight/{flightId}")
    public ResponseEntity<TripCreationDTO> changeFlight(@PathVariable Long id, @PathVariable Long flightId) {
        TripCreationDTO updatedTrip = tripService.changeFlight(id, flightId);
        if (updatedTrip != null) {
            return ResponseEntity.ok(updatedTrip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/selected/{userId}/{startDate}")
    public TripCreationDTO getTripByUserIdAndStartDate(@PathVariable Long userId, @PathVariable String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startD = LocalDate.parse(startDate, formatter);

        return tripService.getTripByUserIdAndStartDate(userId, startD);
    }

}