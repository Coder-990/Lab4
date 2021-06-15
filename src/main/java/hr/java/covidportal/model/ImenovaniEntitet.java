package main.java.hr.java.covidportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Apstrakna klasa koju nasljeđuje svaka klasa koja prima parametar objekt klase tipa String
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class ImenovaniEntitet {

    String naziv;
}
