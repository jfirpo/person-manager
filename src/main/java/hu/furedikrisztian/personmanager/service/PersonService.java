package hu.furedikrisztian.personmanager.service;

import hu.furedikrisztian.personmanager.domain.entity.Person;
import hu.furedikrisztian.personmanager.domain.entity.Address;
import hu.furedikrisztian.personmanager.repository.PersonRepository;
import hu.furedikrisztian.personmanager.repository.AddressRepository;
import hu.furedikrisztian.personmanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepo;
    private final AddressRepository addressRepo;

    public PersonService(PersonRepository personRepo, AddressRepository addressRepo) {
        this.personRepo = personRepo;
        this.addressRepo = addressRepo;
    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Személy nem található id=" + id));
    }

    public Person createPerson(Person person) {
        // Új személy mentése (üres címlistával indul)
        return personRepo.save(person);
    }

    public Person updatePerson(Long id, Person updatedData) {
        Person person = getPersonById(id);
        // Frissítjük a mezőket
        person.setName(updatedData.getName());
        // (Esetleg címeket is frissíthetnénk itt, de azt külön végzzük egy másik metódusban)
        return personRepo.save(person);
    }

    public void deletePerson(Long id) {
        Person person = getPersonById(id);
        personRepo.delete(person);
    }

    public Address addAddressToPerson(Long personId, Address newAddress) {
        if (canSaveTheAddress(personId, newAddress)) {
            addressRepo.save(newAddress);
        } else {
            throw new IllegalStateException("A személynek ID: " + personId + " már van " + newAddress.getType().toString() + " típusú címe rögzítve. ");
        }
        return newAddress;
    }

    private boolean canSaveTheAddress(Long personId, Address newAddress) {
        return getPersonById(personId).getAddresses().stream()
                .noneMatch(address -> address.getType().equals(newAddress.getType()));
    }

    public void removeAddressFromPerson(Long personId, Long addressId) {
        Person person = getPersonById(personId);
        // Megkeressük a megfelelő címet a személy címlistájából
        Optional<Address> addressOpt = person.getAddresses().stream()
                .filter(addr -> addr.getId().equals(addressId))
                .findFirst();
        Address address = addressOpt.orElseThrow(() ->
                new ResourceNotFoundException("A törölni kívánt cím nem található a személynél (id=" + addressId + ")"));
        // Eltávolítjuk a személy cím listájából – orphanRemoval miatt automatikusan törlődik az adatbázisból is
        person.removeAddress(address);
    }
}


