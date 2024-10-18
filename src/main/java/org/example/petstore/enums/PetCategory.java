package org.example.petstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetCategory {

    DOGS(1, "Dogs"),
    CATS(2, "Cats");

    private final int id;
    private final String name;

}
