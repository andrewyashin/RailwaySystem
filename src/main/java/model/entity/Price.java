package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity to table <b>PRICE</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "price")
@NamedQueries({
        @NamedQuery(name = "Price.findAll", query = "select p from Price p"),
        @NamedQuery(name = "Price.findById", query = "select p from Price p where p.id = :id")
})
public class Price {
    private Long id;
    private Double compartmentFactor;
    private Double deluxeFactor;
    private Double berthFactor;

    private Set<Route> routes = new HashSet<>();

    public Price(Double compartmentFactor, Double deluxeFactor, Double berthFactor) {
        this.compartmentFactor = compartmentFactor;
        this.deluxeFactor = deluxeFactor;
        this.berthFactor = berthFactor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "compartmentFactor")
    public Double getCompartmentFactor() {
        return compartmentFactor;
    }

    @Column(name = "deluxeFactor")
    public Double getDeluxeFactor() {
        return deluxeFactor;
    }

    @Column(name = "berthFactor")
    public Double getBerthFactor() {
        return berthFactor;
    }

    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Route> getRoute() {
        return routes;
    }
}
