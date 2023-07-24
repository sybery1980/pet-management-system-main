package fabrics;

import enums.PetType;
import models.Cat;
import models.Dog;
import models.Hamster;
import models.Pet;

public class PetBuilder extends Builder {
    @Override
    protected Pet createNewPet(PetType type) {

        return switch (type) {
            case Cat -> new Cat();
            case Dog -> new Dog();
            case Hamster -> new Hamster();
        };
    }
}
