package hu.furedikrisztian.personmanager.repository;

import hu.furedikrisztian.personmanager.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
