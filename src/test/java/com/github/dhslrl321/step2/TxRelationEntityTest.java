package com.github.dhslrl321.step2;

import com.github.dhslrl321.support.AbstractJpaTest;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.dhslrl321.support.TestUtils.newId;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TxRelationEntityTest extends AbstractJpaTest {

    @Test
    @DisplayName("entityManager 의 flush 를 실행하기 위해서, 모든 연산은 Transaction 으로 묶여야 한다.")
    void name() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        A a = new A(newId());
        B b = new B(newId());

        entityManager.persist(a);
        entityManager.persist(b);

        assertDoesNotThrow(() -> entityManager.flush());
        transaction.commit();
    }
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
class A {
    @Id
    String id;
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
class B {
    @Id
    String id;
}
