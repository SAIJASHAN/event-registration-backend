package com.event.eventregistration;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationRepository repository;

    public RegistrationController(RegistrationRepository repository) {
        this.repository = repository;
    }

    // Register a student
    @PostMapping
    public ResponseEntity<?> register(@RequestBody Registration registration) {

        if (repository.existsByRollNo(registration.getRollNo())) {
            return ResponseEntity
                    .status(409)
                    .body("Roll number already registered");
        }

        Registration saved = repository.save(registration);

        return ResponseEntity
                .status(201)
                .body(saved);
    }

    // Get all registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return repository.findAll();
    }

    // Get registration count
    @GetMapping("/count")
    public long getRegistrationCount() {
        return repository.count();
    }

    // Delete registration
    @DeleteMapping("/{id}")
    public String deleteRegistration(@PathVariable int id) {

        repository.deleteById(id);

        return "Registration deleted successfully";
    }


@GetMapping("/public")
public List<PublicRegistration> getPublicRegistrations() {

    return repository.findAll()
            .stream()
            .map(r -> new PublicRegistration(
                    r.getStudentName(),
                    r.getRollNo()
            ))
            .toList();
}

}