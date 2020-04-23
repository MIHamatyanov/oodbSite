package oodbSite.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
