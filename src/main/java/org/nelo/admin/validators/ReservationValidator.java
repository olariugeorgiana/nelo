package org.nelo.admin.validators;

import org.nelo.entities.ReservationSearch;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReservationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ReservationSearch.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ReservationSearch reservationSearch = (ReservationSearch) target;

        if (reservationSearch.getUserAccount().getFirstName().isEmpty() || reservationSearch.getUserAccount().getLastName().isEmpty() || reservationSearch.getUserAccount().getEmail().isEmpty())
            errors.rejectValue("userAccount.firstName", null, "Complete all the client fields!");

        if (reservationSearch.getStartDate() == null || reservationSearch.getEndDate() == null)
            errors.rejectValue("endDate", null, "Check in and check out date must be completed!");

        if (reservationSearch.getRoomId() == -1) {
            errors.rejectValue("roomId", null, "Select a room for reservation!");
        }
    }
}
