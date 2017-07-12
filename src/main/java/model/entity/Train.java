package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity to table <b>TRAIN</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "train")
@NamedQueries({
        @NamedQuery(name = "Train.findAll", query = "select t from Train t"),
        @NamedQuery(name = "Train.findById", query = "select distinct t from Train t where t.id = :id")
})
public class Train {
    private Long id;

    private Long compartmentFree;
    private Long deluxeFree;
    private Long berthFree;

    private Route route;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "compartmentFree")
    public Long getCompartmentFree() {
        return compartmentFree;
    }

    @Column(name = "deluxeFree")
    public Long getDeluxeFree() {
        return deluxeFree;
    }

    @Column(name = "berthFree")
    public Long getBerthFree() {
        return berthFree;
    }

    @ManyToOne
    @JoinColumn(name = "routeId")
    public Route getRoute() {
        return route;
    }
}
