import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMember, NewMember } from '../member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMember for edit and NewMemberFormGroupInput for create.
 */
type MemberFormGroupInput = IMember | PartialWithRequiredKeyOf<NewMember>;

type MemberFormDefaults = Pick<NewMember, 'id' | 'eventsubscriptions'>;

type MemberFormGroupContent = {
  id: FormControl<IMember['id'] | NewMember['id']>;
  memberUID: FormControl<IMember['memberUID']>;
  firstName: FormControl<IMember['firstName']>;
  lastName: FormControl<IMember['lastName']>;
  middleName: FormControl<IMember['middleName']>;
  email: FormControl<IMember['email']>;
  country: FormControl<IMember['country']>;
  city: FormControl<IMember['city']>;
  address: FormControl<IMember['address']>;
  zipCode: FormControl<IMember['zipCode']>;
  birthDate: FormControl<IMember['birthDate']>;
  signupDate: FormControl<IMember['signupDate']>;
  rank: FormControl<IMember['rank']>;
  eventsubscriptions: FormControl<IMember['eventsubscriptions']>;
  member: FormControl<IMember['member']>;
};

export type MemberFormGroup = FormGroup<MemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberFormService {
  createMemberFormGroup(member: MemberFormGroupInput = { id: null }): MemberFormGroup {
    const memberRawValue = {
      ...this.getFormDefaults(),
      ...member,
    };
    return new FormGroup<MemberFormGroupContent>({
      id: new FormControl(
        { value: memberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      memberUID: new FormControl(memberRawValue.memberUID),
      firstName: new FormControl(memberRawValue.firstName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      lastName: new FormControl(memberRawValue.lastName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      middleName: new FormControl(memberRawValue.middleName, {
        validators: [Validators.maxLength(50)],
      }),
      email: new FormControl(memberRawValue.email, {
        validators: [Validators.required, Validators.maxLength(60)],
      }),
      country: new FormControl(memberRawValue.country, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      city: new FormControl(memberRawValue.city, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      address: new FormControl(memberRawValue.address, {
        validators: [Validators.required, Validators.maxLength(250)],
      }),
      zipCode: new FormControl(memberRawValue.zipCode, {
        validators: [Validators.required, Validators.maxLength(5)],
      }),
      birthDate: new FormControl(memberRawValue.birthDate, {
        validators: [Validators.required],
      }),
      signupDate: new FormControl(memberRawValue.signupDate, {
        validators: [Validators.required],
      }),
      rank: new FormControl(memberRawValue.rank, {
        validators: [Validators.required],
      }),
      eventsubscriptions: new FormControl(memberRawValue.eventsubscriptions ?? []),
      member: new FormControl(memberRawValue.member),
    });
  }

  getMember(form: MemberFormGroup): IMember | NewMember {
    return form.getRawValue() as IMember | NewMember;
  }

  resetForm(form: MemberFormGroup, member: MemberFormGroupInput): void {
    const memberRawValue = { ...this.getFormDefaults(), ...member };
    form.reset(
      {
        ...memberRawValue,
        id: { value: memberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MemberFormDefaults {
    return {
      id: null,
      eventsubscriptions: [],
    };
  }
}
