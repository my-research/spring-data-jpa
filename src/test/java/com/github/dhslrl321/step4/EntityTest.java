package com.github.dhslrl321.step4;

import com.github.dhslrl321.support.AbstractJpaTest;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

public class EntityTest extends AbstractJpaTest {
    @Test
    void name() {
        entityManager.persist(new Foo());
    }
}

@Entity
@Table(name = "foos")
class Foo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Enumerated(EnumType.STRING)
    FooType type;
}

enum FooType {
    NORMAL,
    COMPLEX
}
