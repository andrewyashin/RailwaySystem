package model.entity;

import dao.mysql.TypePlace;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity to table <b>REQUEST</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request")
@NamedQueries({
        @NamedQuery(name = "Request.findAll", query = "select r from Request r"),
        @NamedQuery(name = "Request.findById", query = "select r from Request r where r.id = :id")
})
public class Request implements Serializable{
    private Long id;
    private TypePlace type;
    private Double price;

    private User user;
    private Train train;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public TypePlace getType() {
        return type;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "trainId")
    public Train getTrain() {
        return train;
    }
}
