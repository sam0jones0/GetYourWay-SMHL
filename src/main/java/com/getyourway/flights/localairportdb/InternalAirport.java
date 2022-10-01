package com.getyourway.flights.localairportdb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(
    name = "AIRPORTS",
    indexes = {
      @Index(name = "ICAO", columnList = "ICAO", unique = true),
      @Index(name = "IATA", columnList = "ICAO"),
      @Index(name = "CITY", columnList = "ICAO"),
      @Index(name = "NAME", columnList = "NAME")
    })
public class InternalAirport {

  @Id
  @Column(name = "ICAO", nullable = false)
  private String icao;

  @Column(name = "IATA")
  private String iata;

  @Column(name = "CITY")
  private String city;

  @Column(name = "NAME")
  private String name;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "TIMEZONE")
  private String tz;

  @Column(name = "LATITUDE")
  private float lat;

  @Column(name = "LONGITUDE")
  private float lon;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    InternalAirport airport = (InternalAirport) o;
    return icao != null && Objects.equals(icao, airport.icao);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
