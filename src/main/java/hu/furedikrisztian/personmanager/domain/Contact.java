package hu.furedikrisztian.personmanager.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contactType;

    @Column(nullable = false)
    private String contactValue;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @JsonIgnore  // nehogy visszafelé a cím adatait is beágyazzuk JSON-be
    private Address address;

}
