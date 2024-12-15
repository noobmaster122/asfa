package org.asfa.managerasfa.service.mapper;

import static org.asfa.managerasfa.domain.MemberAsserts.*;
import static org.asfa.managerasfa.domain.MemberTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberMapperTest {

    private MemberMapper memberMapper;

    @BeforeEach
    void setUp() {
        memberMapper = new MemberMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMemberSample1();
        var actual = memberMapper.toEntity(memberMapper.toDto(expected));
        assertMemberAllPropertiesEquals(expected, actual);
    }
}
