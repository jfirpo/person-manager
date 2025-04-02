package hu.furedikrisztian.personmanager.controller;


import hu.furedikrisztian.personmanager.domain.entity.Person;
import hu.furedikrisztian.personmanager.domain.entity.Address;
import hu.furedikrisztian.personmanager.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 1. Összes személy lekérdezése
    @GetMapping
    public List<Person> listPersons() {
        return personService.getAllPersons();
    }

    // 2. Egy személy lekérdezése ID alapján
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    // 3. Új személy létrehozása
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) {
        Person saved = personService.createPerson(newPerson);
        // Visszaadjuk a létrehozott erőforrás adatait, Location header-rel
        URI location = URI.create("/api/persons/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    // 4. Személy adatainak módosítása
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson);
    }

    // 5. Személy törlése
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Új cím hozzáadása a személyhez
    @PostMapping("/{id}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable Long id, @RequestBody Address address) {
        Address savedAddress = personService.addAddressToPerson(id, address);
        URI location = URI.create("/api/persons/" + id + "/addresses/" + savedAddress.getId());
        return ResponseEntity.created(location).body(savedAddress);
    }

    // 7. Adott cím törlése a személytől
    @DeleteMapping("/{personId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personService.removeAddressFromPerson(personId, addressId);
        return ResponseEntity.noContent().build();
    }
}

