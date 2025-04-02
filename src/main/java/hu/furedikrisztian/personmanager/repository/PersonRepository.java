package hu.furedikrisztian.personmanager.repository;
import hu.furedikrisztian.personmanager.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> getPersonById(Long id);
}
