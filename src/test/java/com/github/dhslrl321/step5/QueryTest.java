package com.github.dhslrl321.step5;

import com.github.dhslrl321.support.AbstractJpaTest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.dhslrl321.support.TestUtils.newId;
import static org.assertj.core.api.Assertions.*;

public class QueryTest extends AbstractJpaTest {


    @Test
    @DisplayName("양방향 객체 연관관계")
    void no() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Team1 team = new Team1(newId(), "team", null);
        Member1 member = new Member1(newId(), "member", team); // 연관관계 주인
        team.setMember1(List.of(member));


        entityManager.persist(team);
        entityManager.persist(member);
        entityManager.flush();

        transaction.commit();
    }

    @Test
    @DisplayName("양방향 객체 연관관계에서 연관관계 주인한테 객체를 매핑하지 않으면 예외가 발생한다.")
    void name() {

        EntityTransaction transaction = entityManager.getTransaction();
        Member1 salah = new Member1(newId(), "salah", null); // 연관관계 주인
        Member1 arnold = new Member1(newId(), "arnold", null); // 연관관계 주인

        transaction.begin();
        entityManager.persist(salah);
        entityManager.persist(arnold);

        Team1 team = new Team1(newId(), "Liverpool", null);
        team.setMember1(List.of(salah, arnold));
        entityManager.persist(team);
        entityManager.flush();
        transaction.commit();

        // 조회
        transaction.begin();
        List<Member1> resultList = entityManager.createQuery("select m from member1 m").getResultList();
        transaction.commit();

        assertThat(resultList.get(0).team1).isNull();
        assertThat(resultList.get(1).team1).isNull();
    }
}

@Setter
@Entity(name = "member1")
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Member1 {
    @Id
    String id;
    String name;

    @ManyToOne
    Team1 team1;
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
class Team1 {
    @Id
    String id;
    String name;

    @OneToMany(mappedBy = "team1")
    List<Member1> member1;
}