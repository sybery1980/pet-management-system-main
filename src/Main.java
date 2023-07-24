import controllers.PetController;
import models.Pet;
import repositories.IRepository;
import repositories.PetRepository;
import userinterfaces.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        IRepository<Pet> pet_system = new PetRepository();
        PetController controller = new PetController(pet_system);
        new ConsoleMenu(controller).start();
    }
}