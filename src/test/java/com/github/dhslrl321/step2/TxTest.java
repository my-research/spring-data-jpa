package com.github.dhslrl321.step2;

import com.github.dhslrl321.support.fixture.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TxTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    void name() {
        EntityTransaction transaction = entityManager.getTransaction();

        Member entity = Member.of("A", "heo");

        transaction.begin();
        entityManager.persist(entity); // p.c 저장 -> db 저장
        transaction.commit();

        Member actual = entityManager.find(Member.class, "A");// p.c 에서 옴

        assertThat(entity).isEqualTo(actual);
    }
}
