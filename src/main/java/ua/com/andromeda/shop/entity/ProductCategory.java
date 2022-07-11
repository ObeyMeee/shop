package ua.com.andromeda.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "category_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "category")
    private Set<Product> products;
}