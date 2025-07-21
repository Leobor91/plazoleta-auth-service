package com.pragma.plazadecomidas.authservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String dniNumber;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Role role;

    public boolean isAdult() {
        return birthDate != null &&
                java.time.Period.between(birthDate, LocalDate.now()).getYears() >= 18;

    }
}
