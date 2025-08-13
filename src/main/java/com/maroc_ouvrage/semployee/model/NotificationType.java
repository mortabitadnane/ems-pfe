package com.maroc_ouvrage.semployee.model;

public enum NotificationType {
    LEAVE_REQUEST,
    EMPLOYEE_EVENT, // Employee create/update/delete events
    CONTRACT_EVENT,         // General contract creation/update events
    CONTRACT_EXPIRY,        // Contract nearing expiration
    DEPARTMENT_EVENT,       // Department create/update/delete events
    USER_EVENT,
    ATTENDANCE_ALERT,
    SYSTEM_MESSAGE
}

