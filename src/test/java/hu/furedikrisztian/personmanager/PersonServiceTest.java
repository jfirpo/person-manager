package hu.furedikrisztian.personmanager;


import hu.furedikrisztian.personmanager.domain.entity.Address;
import hu.furedikrisztian.personmanager.domain.entity.Contact;
import hu.furedikrisztian.personmanager.domain.entity.Person;
import hu.furedikrisztian.personmanager.domain.entity.enums.AddressType;
import hu.furedikrisztian.personmanager.repository.AddressRepository;
import hu.furedikrisztian.personmanager.repository.PersonRepository;
import hu.furedikrisztian.personmanager.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPerson_successfully_withAddressAndContact() {
        Person person = new Person("Kiss Béla");

        Address address = new Address();
        address.setType(AddressType.PERMANENT);
        address.setCity("Budapest");
        address.setStreet("Fő utca");

        Contact contact = new Contact();
        contact.setContactType("Email");
        contact.setContactValue("kiss.bela@mail.hu");
        address.setContacts(List.of(contact));

        person.setAddresses(List.of(address));

        when(personRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Person saved = personService.createPerson(person);

        assertNotNull(saved);
        assertEquals(1, saved.getAddresses().size());
        assertEquals("Budapest", saved.getAddresses().get(0).getCity());
        assertEquals("Email", saved.getAddresses().get(0).getContacts().get(0).getContactType());
        assertEquals(saved, saved.getAddresses().get(0).getPerson());
    }

    @Test
    void createPerson_shouldFail_whenDuplicateAddressType() {
        Person person = new Person("Nagy Anna");

        Address a1 = new Address();
        a1.setType(AddressType.PERMANENT);
        a1.setCity("Bp");
        a1.setStreet("utca 1");

        Address a2 = new Address();
        a2.setType(AddressType.PERMANENT);
        a2.setCity("Bp");
        a2.setStreet("utca 2");

        person.setAddresses(List.of(a1, a2));

        assertThrows(IllegalArgumentException.class, () -> personService.createPerson(person));
    }

}
