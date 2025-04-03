package hu.furedikrisztian.personmanager.repository;
import hu.furedikrisztian.personmanager.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
