package com.github.lucasgms.usermanagement.features.user.domain.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(name = "unique_cnpj", columnNames = {"cnpj"}),
        @UniqueConstraint(name = "unique_cpf", columnNames = { "cpf" })
})
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String telephone;

    private Instant createdAt;

    private Instant updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
