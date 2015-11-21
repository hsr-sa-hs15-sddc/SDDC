package sddc.services;


import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.Identifier;

public interface IdentifierRepo extends JpaRepository<Identifier, Long> {
}
