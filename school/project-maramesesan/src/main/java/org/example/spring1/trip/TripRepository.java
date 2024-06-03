package org.example.spring1.trip;

import jakarta.transaction.Transactional;
import org.example.spring1.flight.model.Flight;
import org.example.spring1.location.model.Location;
import org.example.spring1.trip.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TripRepository extends JpaRepository<Trip, Long> {

//    @Query(value = "INSERT INTO trip_locations (trip_id, location_id) VALUES (:tripId, :locationId)", nativeQuery = true)
//    void addLocationToTrip(Long tripId, Long locationId);

    Trip getTripById(Long id);
    Trip getTripByUserIdAndStartDate(Long userId, LocalDate startDate);
}
