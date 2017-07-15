package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Entity to table <b>ROUTE</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route")
@NamedQueries({
        @NamedQuery(name = "Route.findAll",
                query = "select r from Route r"),
        @NamedQuery(name = "Route.findById",
                query = "select r from Route r where r.id = :id"),
        @NamedQuery(name = "Route.findByFromId",
                query = "select r from Route r where r.fromStation.id = :fromId")
})
public class Route implements Serializable{
    private Long id;
    private String fromTime;
    private String toTime;
    private Double distance;

    private Price price;
    private Station fromStation;
    private Station toStation;
    private Set<Train> trains;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }


    @Column(name = "fromTime")
    public String getFromTime() {
        return fromTime;
    }

    @Column(name = "toTime")
    public String getToTime() {
        return toTime;
    }

    @Column(name = "distance")
    public Double getDistance() {
        return distance;
    }

    @ManyToOne
    @JoinColumn(name = "priceId")
    public Price getPrice() {
        return price;
    }

    @ManyToOne
    @JoinColumn(name = "fromId")
    public Station getFromStation() {
        return fromStation;
    }

    @ManyToOne
    @JoinColumn(name = "toId")
    public Station getToStation() {
        return toStation;
    }

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Train> getTrains(){
        return trains;
    }
}
