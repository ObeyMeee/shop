package ua.com.andromeda.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String zipCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
