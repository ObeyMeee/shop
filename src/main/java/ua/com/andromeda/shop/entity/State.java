package ua.com.andromeda.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "state")
@Getter
@Setter
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id")
    Country country;
}
