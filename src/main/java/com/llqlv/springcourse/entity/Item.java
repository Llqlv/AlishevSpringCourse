package com.llqlv.springcourse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(itemName, item.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName);
    }
}
