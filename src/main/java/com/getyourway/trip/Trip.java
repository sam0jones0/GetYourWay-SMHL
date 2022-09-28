package com.getyourway.trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getyourway.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Column(nullable=false, unique=true)
    private @Id
    @GeneratedValue Long id;

    private String tripName; //

    @Column
    private String departureAirport;

    @Column
    private String destinationAirport;

    @Column
    private LocalDateTime departureDateTime;

    @Column
    private LocalDateTime arrivalDateTime;
    //private  (type TBD) weatherInstance;
    //private (some sort of link to local travel api, eg, trains/transport to airport)



    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Do not return parent (User) in HttpResponses
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // Required if using 'FetchType.Lazy
    private User user;

    public Trip() {}


    public Trip(String tripName) {
        this.tripName = tripName;
    }

    public Trip(String tripName, String departureAirport, String destinationAirport, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        this.tripName = tripName;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
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

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
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
