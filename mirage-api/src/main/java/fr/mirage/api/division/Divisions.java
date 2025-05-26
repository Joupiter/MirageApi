package fr.mirage.api.division;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Divisions implements Division {

    EXAMPLE ("example", 0);

    private final String name;
    private final int requiredElo;

}