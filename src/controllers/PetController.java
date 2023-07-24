package controllers;

import enums.PetType;
import fabrics.Builder;
import fabrics.PetBuilder;
import models.Pet;
import repositories.IRepository;
import repositories.PetRepository;
import userinterfaces.ConsoleView;
import userinterfaces.View;
import validators.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PetController {
    private IRepository<Pet> petRepository;
    private Builder petCreator;
    private final View<Pet> view;
    private Validator validator;

    public PetController(IRepository<Pet> petRepository) {
        this.petRepository = petRepository;
        this.petCreator = new PetBuilder();
        this.view = new ConsoleView();
        this.validator = new Validator();
    }

    public void createPet(PetType type) {

        String[] data = new String[]{view.getName(), view.getBirthday()};
        validator.validate(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = petRepository.create(petCreator.createPet(type, data[0], birthday));
            view.showMessage(String.format("%d запись добавлена", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void updatePet(int id) {

        Pet pet = getById(id);
        String[] data = new String[]{view.getName(), view.getBirthday()};

        validator.validate(data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        pet.setName(data[0]);
        pet.setBirthday(birthday);
        try {
            int res = petRepository.update(pet);
            view.showMessage(String.format("%d запись изменена \n", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void getAllPet() {
        try {
            view.printAll(petRepository.getAll(), Pet.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public boolean trainPet(int id, String command) {
        try {

            if (((PetRepository) petRepository).getCommandsById(id).contains(command))
                view.showMessage("Это команда уже разучена");
            else {
                ((PetRepository) petRepository).train(id, command);
                view.showMessage("команда разучена\n");
                return true;

            }
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return false;
    }

    public Pet getById(int id) {
        try {
            return petRepository.getById(id);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return null;
    }

    public void delete(int id) {
        try {
            petRepository.delete(id);
            view.showMessage("запись удалена");
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void getCommands(int id) {
        try {
            view.printAll(((PetRepository) petRepository).getCommandsById(id), String.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }
}
