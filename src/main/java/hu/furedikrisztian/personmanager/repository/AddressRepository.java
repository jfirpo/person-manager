package hu.furedikrisztian.personmanager.repository;

import hu.furedikrisztian.personmanager.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
