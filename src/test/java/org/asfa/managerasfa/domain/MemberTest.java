package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;
import static org.asfa.managerasfa.domain.MemberTestSamples.*;
import static org.asfa.managerasfa.domain.MemberTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = getMemberSample1();
        Member member2 = new Member();
        assertThat(member1).isNotEqualTo(member2);

        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);

        member2 = getMemberSample2();
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    void familyMembersTest() {
        Member member = getMemberRandomSampleGenerator();
        Member memberBack = getMemberRandomSampleGenerator();

        member.addFamilyMembers(memberBack);
        assertThat(member.getFamilyMembers()).containsOnly(memberBack);
        assertThat(memberBack.getMember()).isEqualTo(member);

        member.removeFamilyMembers(memberBack);
        assertThat(member.getFamilyMembers()).doesNotContain(memberBack);
        assertThat(memberBack.getMember()).isNull();

        member.familyMembers(new HashSet<>(Set.of(memberBack)));
        assertThat(member.getFamilyMembers()).containsOnly(memberBack);
        assertThat(memberBack.getMember()).isEqualTo(member);

        member.setFamilyMembers(new HashSet<>());
        assertThat(member.getFamilyMembers()).doesNotContain(memberBack);
        assertThat(memberBack.getMember()).isNull();
    }

    @Test
    void eventsubscriptionTest() {
        Member member = getMemberRandomSampleGenerator();
        EventSubscription eventSubscriptionBack = getEventSubscriptionRandomSampleGenerator();

        member.addEventsubscription(eventSubscriptionBack);
        assertThat(member.getEventsubscriptions()).containsOnly(eventSubscriptionBack);

        member.removeEventsubscription(eventSubscriptionBack);
        assertThat(member.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);

        member.eventsubscriptions(new HashSet<>(Set.of(eventSubscriptionBack)));
        assertThat(member.getEventsubscriptions()).containsOnly(eventSubscriptionBack);

        member.setEventsubscriptions(new HashSet<>());
        assertThat(member.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);
    }

    @Test
    void memberTest() {
        Member member = getMemberRandomSampleGenerator();
        Member memberBack = getMemberRandomSampleGenerator();

        member.setMember(memberBack);
        assertThat(member.getMember()).isEqualTo(memberBack);

        member.member(null);
        assertThat(member.getMember()).isNull();
    }
}
