package org.example.spring1.trip;

import lombok.RequiredArgsConstructor;
import org.example.spring1.accommodation.persistance.AccommodationRepository;
import org.example.spring1.exception.EntityNotFoundException;
import org.example.spring1.flight.FlightRepository;
import org.example.spring1.flight.model.Flight;
import org.example.spring1.flight.model.dto.FlightDTO;
import org.example.spring1.location.LocationRepository;
import org.example.spring1.location.model.Location;
import org.example.spring1.room.RoomRepository;
import org.example.spring1.room.model.Room;
import org.example.spring1.room.model.dto.RoomDTO;
import org.example.spring1.trip.model.Trip;
import org.example.spring1.trip.model.dto.TripCreationDTO;
import org.example.spring1.trip.model.dto.TripDTO;
import org.example.spring1.user.UserRepository;
import org.example.spring1.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final FlightRepository flightRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    public List<TripDTO> findAll() {
        return tripRepository.findAll().stream()
                .map(tripMapper::toTripDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> get(Long id) {

        return tripRepository.findById(id)
                .map(tripMapper::toTripDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public TripCreationDTO create(TripCreationDTO dto, List<Long> locationIds, Long roomId, List<Long> flightIds) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(dto.getEndDate(), formatter);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

//        Trip newTrip = tripMapper.toEntityFromCreation(dto);
//        float finalPrice = calculateFinalPrice(newTrip);
//        System.out.println(finalPrice);

        Trip trip = Trip.builder()
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .build();

        Set<Location> locations = new HashSet<>();
        if (dto.getLocations().isEmpty()) {
            locationIds.forEach(locationId -> {
                Location location = locationRepository.findById(locationId)
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + locationId));
                locations.add(location);
            });
        } else {
            dto.getLocations().forEach(location -> {
                Location flt = locationRepository.findByName(location.getName())
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found with company: " + location.getName()));
                locations.add(flt);
            });
        }
        trip.setLocations(locations);

        Set<Flight> flights = new HashSet<>();
        if (dto.getFlights().isEmpty()) {
            flightIds.forEach(flightId -> {
                Flight flight = flightRepository.findById(flightId)
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));
                flights.add(flight);
            });
        } else {
            dto.getFlights().forEach(flight -> {
                Flight flt = flightRepository.findByCompany(flight.getCompany())
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found with company: " + flight.getCompany()));
                flights.add(flt);
            });
        }
        trip.setFlights(flights);

        Set<Room> rooms = new HashSet<>();
        if (dto.getRooms().isEmpty()) {
            Room defaultRoom = roomRepository.findById(roomId)
                    .orElseThrow(() -> new EntityNotFoundException("Accommodation not found with id: " + roomId));
            rooms.add(defaultRoom);
        } else {
            dto.getRooms().forEach(room -> {
                Room ro = roomRepository.findById(room.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Accommodation not found with id: " + room.getId()));
                rooms.add(ro);
            });
        }
        trip.setRooms(rooms);

        //Trip newTrip = tripMapper.toEntityFromCreation(dto);
        float finalPrice = calculateFinalPrice(trip);
        trip.setPrice(finalPrice);


        Trip savedTrip = tripRepository.save(trip);
        return tripMapper.toTripCreationDto(savedTrip);
    }

    public void delete(Long id) {
        tripRepository.deleteById(id);
    }

//    public List<TripDTO> findAllFiltered(Date startDate, Date endDate) {
//        List<Trip> filteredTrips = tripRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
//        return filteredTrips.stream()
//                .map(tripMapper::toTripDto)
//                .collect(Collectors.toList());
//    }

    public void deleteMultiple(List<Long> ids) {
        for (Long id : ids) {
            tripRepository.deleteById(id);
        }
    }


    public TripCreationDTO changeFlight (Long id, Long flightId){
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        Set<Flight> flights = new HashSet<>();
        Optional<Flight> f1 = flightRepository.findById(flightId);
        flights.add(f1.get());
        if(optionalTrip.isPresent()){
            Trip trip = optionalTrip.get();
            trip.setFlights(flights);
            Trip updateTrip = tripRepository.save(trip);
            return tripMapper.toTripCreationDto(updateTrip);
        }else
            return null;

    }


   public TripCreationDTO getTripByUserIdAndStartDate(Long userId, LocalDate startDate){
       Trip trip = tripRepository.getTripByUserIdAndStartDate(userId, startDate);
       return tripMapper.toTripCreationDto(trip);
   }

    public float calculateFinalPrice(Trip trip) {
        float totalPrice = 0;

        if (trip.getFlights() != null) {
            for (Flight flight : trip.getFlights()) {
                totalPrice += flight.getPrice();
            }
        }

        if (trip.getRooms() != null) {
            for (Room room : trip.getRooms()) {
                totalPrice += room.getPrice();
            }
        }

        return totalPrice;
    }
}
