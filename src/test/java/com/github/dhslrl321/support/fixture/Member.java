package com.github.dhslrl321.support.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.github.dhslrl321.support.TestUtils.newId;

@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    String id;
    String name;

    public static Member of(String name) {
        return new Member(newId(), name);
    }

    public static Member of(String id, String name) {
        return new Member(id, name);
    }
}
