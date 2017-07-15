package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
        @NamedQuery(name = "Train.findAll",
                query = "select t from Train t"),
        @NamedQuery(name = "Train.findById",
                query = "select distinct t from Train t where t.id = :id"),
        @NamedQuery(name = "Train.findByRouteId",
                query = "select distinct t from Train t where t.route.id = :routeId")
})
public class Train implements Serializable{
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
