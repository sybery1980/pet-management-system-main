package fabrics;

import enums.PetType;
import models.Pet;

import java.time.LocalDate;

public abstract class Builder {
    protected abstract Pet createNewPet(PetType type);

    public Pet createPet(PetType type, String name, LocalDate date) {

        Pet pet = createNewPet(type);
        pet.setName(name);
        pet.setBirthday(date);
        return pet;
    }
}
