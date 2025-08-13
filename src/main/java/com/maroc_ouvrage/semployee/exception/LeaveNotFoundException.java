package com.maroc_ouvrage.semployee.exception;

public class LeaveNotFoundException extends RuntimeException {
    public LeaveNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
