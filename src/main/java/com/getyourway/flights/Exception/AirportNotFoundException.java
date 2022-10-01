package com.getyourway.flights.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Airport not found")
public class AirportNotFoundException extends NoSuchElementException {}
