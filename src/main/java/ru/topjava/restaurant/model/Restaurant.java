package ru.topjava.restaurant.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "id", name = "rests_unique_idx")})
public class Restaurant extends AbstractNamedEntity {
    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
