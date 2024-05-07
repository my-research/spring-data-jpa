package com.github.dhslrl321.step5;

import com.github.dhslrl321.support.AbstractJpaTest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.dhslrl321.support.TestUtils.newId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MappingTest extends AbstractJpaTest {

    @Test
    @DisplayName(" 연관관계 매핑 시 모든 엔티티가 영속상태가 아니면 예외가 발생한다.")
    void no() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Team team = new Team(newId(), "fooTeam"); // 비영속 상태 (transient)
        Member member = new Member(newId(), "foo", team);

        entityManager.persist(member); // 영속 상태 (managed)
        assertThatThrownBy(() -> entityManager.flush());
        transaction.commit();
    }
}

@Entity(name = "members")
@NoArgsConstructor
@AllArgsConstructor
class Member {
    @Id
    String id;
    String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
class Team {
    @Id
    String id;
    String name;
}
