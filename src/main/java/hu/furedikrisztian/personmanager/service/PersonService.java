package hu.furedikrisztian.personmanager.service;

import hu.furedikrisztian.personmanager.domain.entity.Person;
import hu.furedikrisztian.personmanager.domain.entity.Address;
import hu.furedikrisztian.personmanager.repository.PersonRepository;
import hu.furedikrisztian.personmanager.repository.AddressRepository;
import hu.furedikrisztian.personmanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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

    // Üzleti logika: Új cím hozzáadása egy személyhez, 2 cím limit figyelembevételével
    public Address addAddressToPerson(Long personId, Address newAddress) {
        Person person = getPersonById(personId);
        List<Address> addressList = new ArrayList<>();
        addressList.add(newAddress);
        if (person.getAddresses().size() >= 2) {
            throw new IllegalStateException("Egy személynél legfeljebb 2 cím rögzíthető (limit túllépve)!");
        }
        // opcionális: ellenőrizhetjük, hogy ugyanilyen típusú cím már létezik-e ennél a személynél
        person.setAddresses(addressList);
        // mentjük a új cím entitást (a cascadelés miatt elég a person mentése is)
        addressRepo.save(newAddress);
        return newAddress;
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


