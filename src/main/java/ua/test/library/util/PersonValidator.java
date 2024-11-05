package ua.test.library.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.test.library.dao.PeopleDAO;
import ua.test.library.model.Person;

@Component
public class PersonValidator implements Validator {
    private PeopleDAO peopleDAO;
@Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleDAO.readByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "",
                    "This fullName is already taken");
        }
    }
}
