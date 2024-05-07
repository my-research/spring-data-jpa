package com.github.dhslrl321.step5;

import com.github.dhslrl321.support.AbstractJpaTest;
import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.dhslrl321.support.TestUtils.newId;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryTest extends AbstractJpaTest {


    @Test
    @DisplayName("양방향 객체 연관관계")
    void no() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Team1 team = new Team1(newId(), "team", null);
        Member1 member = new Member1(newId(), "member", team); // 연관관계 주인
        team.setMember1(member);


        entityManager.persist(team);
        entityManager.persist(member);
        entityManager.flush();

        transaction.commit();
    }

    @Test
    @DisplayName("양방향 객체 연관관계에서 연관관계 주인이 아닌 쪽에 값을 주입하면 외래키 설정이 무시된다.")
    void name() {

        EntityTransaction transaction = entityManager.getTransaction();
        Member1 salah = new Member1(newId(), "salah", null); // 연관관계 주인
        Member1 arnold = new Member1(newId(), "arnold", null); // 연관관계 주인

        transaction.begin();
        entityManager.persist(salah);
        entityManager.persist(arnold);

        Team1 team = new Team1(newId(), "Liverpool", new ArrayList<>());
        // 무시
        team.setMember1(salah);
        team.setMember1(arnold);

        entityManager.persist(team);
        entityManager.flush();
        transaction.commit();

        // 조회
        transaction.begin();
        List<Member1> resultList = entityManager.createQuery("select m from member1 m").getResultList();
        transaction.commit();

        System.out.println(resultList);
        assertThat(resultList.get(0).team1).isNull();
        assertThat(resultList.get(1).team1).isNull();
    }

    @Test
    @DisplayName("양방향 객체 연관관계에서 연관관계 주인한테 값을 주입해야 외래키가 설정된다.")
    void name1() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Team1 team = new Team1(newId(), "Liverpool", new ArrayList<>());
        entityManager.persist(team);

        Member1 salah = new Member1(newId(), "salah",null); // 연관관계 주인
        salah.setTeam1(team);
        entityManager.persist(salah);

        Member1 arnold = new Member1(newId(), "arnold", null); // 연관관계 주인
        arnold.setTeam1(team);
        entityManager.persist(arnold);

        entityManager.flush();
        transaction.commit();

        // 조회
        transaction.begin();
        List<Member1> member_result = entityManager.createQuery("select m from member1 m").getResultList();
        transaction.commit();

        assertThat(member_result.get(0).team1.getId()).isEqualTo(team.getId());
        assertThat(member_result.get(1).team1.getId()).isEqualTo(team.getId());
    }

    @Test
    @DisplayName("양방향 연관관계는 양쪽 모두 관계를 맺어주자.")
    void name2() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Team1 team = new Team1(newId(), "Liverpool", new ArrayList<>());
        entityManager.persist(team);

        Member1 salah = new Member1(newId(), "salah",null); // 연관관계 주인
        salah.setTeam1(team);
        team.setMember1(salah);
        entityManager.persist(salah);

        Member1 arnold = new Member1(newId(), "arnold", null); // 연관관계 주인
        arnold.setTeam1(team);
        team.setMember1(arnold);
        entityManager.persist(arnold);

        entityManager.flush();
        transaction.commit();

        transaction.begin();
        List<Member1> resultList = entityManager.createQuery("select m from member1 m").getResultList();
        transaction.commit();

        assertThat(resultList.get(0).team1).isNotNull();
        assertThat(resultList.get(1).team1).isNotNull();
        assertThat(team.getMember1().size()).isEqualTo(2);
    }
}

@Setter
@Entity(name = "member1")
@NoArgsConstructor
@AllArgsConstructor
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
@Getter
@ToString
class Team1 {
    @Id
    String id;
    String name;

    @OneToMany(mappedBy = "team1")
    List<Member1> member1;

    public void setMember1(Member1 member1) {
        this.member1.add(member1);
    }
}