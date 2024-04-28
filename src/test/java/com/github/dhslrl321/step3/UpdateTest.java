package com.github.dhslrl321.step3;

import com.github.dhslrl321.support.AbstractJpaTest;
import com.github.dhslrl321.support.fixture.Member;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Query;

public class UpdateTest extends AbstractJpaTest {

    @Test
    void name() {
        Member entity = Member.of("hello");

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(entity);

        entity.setName("heo");

        transaction.commit();
    }

    @Test
    void name2() {
        Member entity = Member.of("A", "heo");
        entityManager.persist(entity);
        entityManager.persist(Member.of("jang"));

        entityManager.createQuery("SELECT m FROM Member m where ?: entity", Member.class); // flush
    }
}
