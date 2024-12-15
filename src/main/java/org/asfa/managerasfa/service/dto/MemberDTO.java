package org.asfa.managerasfa.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.asfa.managerasfa.domain.enumeration.FamilyRank;

/**
 * A DTO for the {@link org.asfa.managerasfa.domain.Member} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberDTO implements Serializable {

    private Long id;

    private UUID memberUID;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String middleName;

    @NotNull
    @Size(max = 60)
    private String email;

    @NotNull
    @Size(max = 50)
    private String country;

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Size(max = 250)
    private String address;

    @NotNull
    @Size(max = 5)
    private String zipCode;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate signupDate;

    @NotNull
    private FamilyRank rank;

    private Set<EventSubscriptionDTO> eventsubscriptions = new HashSet<>();

    private MemberDTO member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getMemberUID() {
        return memberUID;
    }

    public void setMemberUID(UUID memberUID) {
        this.memberUID = memberUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    public FamilyRank getRank() {
        return rank;
    }

    public void setRank(FamilyRank rank) {
        this.rank = rank;
    }

    public Set<EventSubscriptionDTO> getEventsubscriptions() {
        return eventsubscriptions;
    }

    public void setEventsubscriptions(Set<EventSubscriptionDTO> eventsubscriptions) {
        this.eventsubscriptions = eventsubscriptions;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberDTO)) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + getId() +
            ", memberUID='" + getMemberUID() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", email='" + getEmail() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", address='" + getAddress() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", signupDate='" + getSignupDate() + "'" +
            ", rank='" + getRank() + "'" +
            ", eventsubscriptions=" + getEventsubscriptions() +
            ", member=" + getMember() +
            "}";
    }
}
