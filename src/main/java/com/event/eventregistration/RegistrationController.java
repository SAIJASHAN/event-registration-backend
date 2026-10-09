
package com.event.eventregistration;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public RegistrationController(
            RegistrationRepository repository,
            SimpMessagingTemplate messagingTemplate) {

        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    // Register a student
    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody Registration registration) {

        if (repository.existsByRollNo(registration.getRollNo())) {

            return ResponseEntity
                    .status(409)
                    .body("You are already a Member, Thank you");
        }

        Registration saved = repository.save(registration);

        // Get the updated registration count
        long count = repository.count();

        // Broadcast the count to connected clients
        messagingTemplate.convertAndSend(
                "/topic/registration-count",
                count
        );

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

    // Get public registration details
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
