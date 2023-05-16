package com.llqlv.springcourse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    @Column(name = "name")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email);
    }
}
