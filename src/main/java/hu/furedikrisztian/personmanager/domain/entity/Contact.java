package hu.furedikrisztian.personmanager.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contactType;

    @Column(nullable = false)
    private String contactValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @JsonIgnore  // nehogy visszafelé a cím adatait is beágyazzuk JSON-be
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

}
