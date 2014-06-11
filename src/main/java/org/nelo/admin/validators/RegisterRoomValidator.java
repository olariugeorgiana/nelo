package org.nelo.admin.validators;


import org.nelo.dao.RoomDao;
import org.nelo.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterRoomValidator implements Validator {

    String NUMBER_REGEX = ".*[^0-9].*";

    @Autowired
    private RoomDao roomDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Room.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomNumber", null, "Number must be completed!");

        if (!(((Room) target).getRoomNumber().matches(NUMBER_REGEX))) {

            Room roomByNumber = roomDao.getByNumber(((Room) target));

            if (roomByNumber != null)
                errors.rejectValue("roomNumber", null, "Room already saved with this number!");

        } else errors.rejectValue("roomNumber", null, "You must complete only numbers!");

        Room room = (Room) target;

        if (room.getRoomType().equals("-1"))
            errors.rejectValue("roomType", null, "Select room type!");

    }

}
