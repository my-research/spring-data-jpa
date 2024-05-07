package com.github.dhslrl321.step3;

import com.github.dhslrl321.support.AbstractJpaTest;
import com.github.dhslrl321.support.fixture.Member;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityInformationTest extends AbstractJpaTest {

    @Test
    void name() {
        Member entity = Member.of("heo");
        JpaEntityInformation<Member, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(Member.class, entityManager);
        assertThat(entityInformation.isNew(entity)).isFalse(); // jpa 에서는 entity id 가 없을 때 new 라고 판단, 이를 해결하기 위해서는 Persistable interface 재정의를 해야함

        entityManager.persist(entity);
        assertThat(entityInformation.isNew(entity)).isFalse();

        entityManager.detach(entity);
        assertThat(entityInformation.isNew(entity)).isFalse();
    }
}
