package com.getyourway.trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getyourway.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {

    @Column(nullable=false, unique=true)
    private @Id
    @GeneratedValue Long id;

    private String tripName; //TODO: Decide and create real attributes

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Do not return parent (User) in HttpResponses
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // Required if using 'FetchType.Lazy
    private User user;

    public Trip() {}

    public Trip(String tripName) {
        this.tripName = tripName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Hash Code, Equals and toString
    // TODO: make hash code, equals and toString once attributes have been decided. See User for example
}
