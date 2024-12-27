package org.asfa.managerasfa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.asfa.managerasfa.domain.enumeration.FamilyRank;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "member_uid", updatable = false)
    private UUID memberUID;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Size(max = 50)
    @Column(name = "middle_name", length = 50)
    private String middleName;

    @NotNull
    @Size(max = 60)
    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @NotNull
    @Size(max = 50)
    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @NotNull
    @Size(max = 250)
    @Column(name = "address", length = 250, nullable = false)
    private String address;

    @NotNull
    @Size(max = 5)
    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name = "signup_date", nullable = false)
    private LocalDate signupDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    private FamilyRank rank;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @JsonIgnoreProperties(value = { "familyMembers", "eventsubscriptions", "member" }, allowSetters = true)
    private Set<Member> familyMembers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_member__eventsubscription",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "eventsubscription_id")
    )
    @JsonIgnoreProperties(value = { "types", "payment", "members", "products" }, allowSetters = true)
    private Set<EventSubscription> eventsubscriptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "familyMembers", "eventsubscriptions", "member" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @PrePersist
    public void generateMemberUID() {
        this.memberUID = UUID.randomUUID();
    }

    public Long getId() {
        return this.id;
    }

    public Member id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getMemberUID() {
        return this.memberUID;
    }

    public Member memberUID(UUID memberUID) {
        this.setMemberUID(memberUID);
        return this;
    }

    public void setMemberUID(UUID memberUID) {
        this.memberUID = memberUID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Member firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Member lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Member middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return this.email;
    }

    public Member email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return this.country;
    }

    public Member country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public Member city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return this.address;
    }

    public Member address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Member zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Member birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getSignupDate() {
        return this.signupDate;
    }

    public Member signupDate(LocalDate signupDate) {
        this.setSignupDate(signupDate);
        return this;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    public FamilyRank getRank() {
        return this.rank;
    }

    public Member rank(FamilyRank rank) {
        this.setRank(rank);
        return this;
    }

    public void setRank(FamilyRank rank) {
        this.rank = rank;
    }

    public Set<Member> getFamilyMembers() {
        return this.familyMembers;
    }

    public void setFamilyMembers(Set<Member> members) {
        if (this.familyMembers != null) {
            this.familyMembers.forEach(i -> i.setMember(null));
        }
        if (members != null) {
            members.forEach(i -> i.setMember(this));
        }
        this.familyMembers = members;
    }

    public Member familyMembers(Set<Member> members) {
        this.setFamilyMembers(members);
        return this;
    }

    public Member addFamilyMembers(Member member) {
        this.familyMembers.add(member);
        member.setMember(this);
        return this;
    }

    public Member removeFamilyMembers(Member member) {
        this.familyMembers.remove(member);
        member.setMember(null);
        return this;
    }

    public Set<EventSubscription> getEventsubscriptions() {
        return this.eventsubscriptions;
    }

    public void setEventsubscriptions(Set<EventSubscription> eventSubscriptions) {
        this.eventsubscriptions = eventSubscriptions;
    }

    public Member eventsubscriptions(Set<EventSubscription> eventSubscriptions) {
        this.setEventsubscriptions(eventSubscriptions);
        return this;
    }

    public Member addEventsubscription(EventSubscription eventSubscription) {
        this.eventsubscriptions.add(eventSubscription);
        return this;
    }

    public Member removeEventsubscription(EventSubscription eventSubscription) {
        this.eventsubscriptions.remove(eventSubscription);
        return this;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return getId() != null && getId().equals(((Member) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
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
            "}";
    }
}
