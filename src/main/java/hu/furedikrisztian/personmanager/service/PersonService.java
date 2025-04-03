package hu.furedikrisztian.personmanager.service;

import hu.furedikrisztian.personmanager.domain.entity.Contact;
import hu.furedikrisztian.personmanager.domain.entity.Person;
import hu.furedikrisztian.personmanager.domain.entity.Address;
import hu.furedikrisztian.personmanager.repository.PersonRepository;
import hu.furedikrisztian.personmanager.repository.AddressRepository;
import hu.furedikrisztian.personmanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
                .orElseThrow(() -> new ResourceNotFoundException("Személy nem található id: " + id));
    }

    public Person createPerson(Person person) {
        if (person.getAddresses() != null) {
            validateAddressTypes(person.getAddresses());
            for (Address address : person.getAddresses()) {
                address.setId(null);
                address.setPerson(person);

                if (address.getContacts() != null) {
                    for (Contact contact : address.getContacts()) {
                        contact.setId(null);
                        contact.setAddress(address);
                    }
                }
            }
        }
        return personRepo.save(person);
    }

    public Person updatePerson(Long id, Person updatedData) {
        Person person = getPersonById(id);

        person.setName(updatedData.getName());

        person.getAddresses().clear();

        if (updatedData.getAddresses() != null) {
            validateAddressTypes(updatedData.getAddresses());
            for (Address address : updatedData.getAddresses()) {
                address.setId(null);
                address.setPerson(person);

                if (address.getContacts() != null) {
                    for (Contact contact : address.getContacts()) {
                        contact.setId(null);
                        contact.setAddress(address);
                    }
                }
                person.getAddresses().add(address);
            }
        }

        return personRepo.save(person);
    }

    public void deletePerson(Long id) {
        Person person = getPersonById(id);
        personRepo.delete(person);
    }

    public Address addAddressToPerson(Long personId, Address newAddress) {
        Person person = getPersonById(personId);
        if (canSaveTheAddress(personId, newAddress)) {
            newAddress.setPerson(person);
            if (newAddress.getContacts() != null) {
                for (Contact contact : newAddress.getContacts()) {
                    contact.setAddress(newAddress);
                }
            }
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
        Optional<Address> addressOpt = person.getAddresses().stream()
                .filter(addr -> addr.getId().equals(addressId))
                .findFirst();
        Address address = addressOpt.orElseThrow(() ->
                new ResourceNotFoundException("A törölni kívánt cím nem található a személynél (id="  + addressId + ")"));
        person.removeAddress(address);
    }

    private void validateAddressTypes(List<Address> addresses) {
        Set<String> types = new HashSet<>();
        for (Address address : addresses) {
            String type = address.getType().name();
            if (!types.add(type)) {
                throw new IllegalArgumentException("Minden cím típusból legfeljebb egy lehet. Duplikált: " + type);
            }
        }
    }
}
