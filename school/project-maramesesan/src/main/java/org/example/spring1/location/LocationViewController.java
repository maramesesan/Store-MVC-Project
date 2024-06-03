package org.example.spring1.location;

import lombok.RequiredArgsConstructor;
import org.example.spring1.location.model.dto.LocationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.example.spring1.UrlMapping.LOCATIONS;

@Controller
@RequestMapping(LOCATIONS)
@RequiredArgsConstructor
public class LocationViewController {
    private final LocationService locationService;

    @GetMapping("/view")
    public ModelAndView getLocationView() {
        ModelAndView mav = new ModelAndView("locationsView");
        List<LocationDTO> locations = locationService.findAll();
        mav.addObject("locations", locations);
        return mav;
    }

      //  @GetMapping("/")
public ModelAndView showLocationDetails(@RequestParam Long id) {
    ModelAndView modelAndView = new ModelAndView("location-details");
    modelAndView.addObject("locationId", id);
    return modelAndView;
}


}
