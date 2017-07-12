package model.entity;

import dao.mysql.TypePlace;
import lombok.*;

import javax.persistence.*;

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
public class Request {
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "train_id")
    public Train getTrain() {
        return train;
    }
}
