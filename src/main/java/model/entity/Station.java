package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity to table <b>STATION</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station")
@NamedQueries({
        @NamedQuery(name = "Station.findAll",
                query = "select s from Station s"),
        @NamedQuery(name = "Station.findById",
                query = "select distinct  s from Station s where s.id = :id")
})
public class Station implements Serializable{
    private Long id;
    private String name;

    private Set<Route> fromRoutes = new HashSet<>();
    private Set<Route> toRoutes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @OneToMany
    @JoinColumn(name = "fromId")
    public Set<Route> getFromRoutes() {
        return fromRoutes;
    }

    @OneToMany
    @JoinColumn(name = "toId")
    public Set<Route> getToRoutes() {
        return toRoutes;
    }
}
