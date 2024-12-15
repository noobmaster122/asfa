import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISubscriptionType, NewSubscriptionType } from '../subscription-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubscriptionType for edit and NewSubscriptionTypeFormGroupInput for create.
 */
type SubscriptionTypeFormGroupInput = ISubscriptionType | PartialWithRequiredKeyOf<NewSubscriptionType>;

type SubscriptionTypeFormDefaults = Pick<NewSubscriptionType, 'id'>;

type SubscriptionTypeFormGroupContent = {
  id: FormControl<ISubscriptionType['id'] | NewSubscriptionType['id']>;
  label: FormControl<ISubscriptionType['label']>;
  summary: FormControl<ISubscriptionType['summary']>;
};

export type SubscriptionTypeFormGroup = FormGroup<SubscriptionTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubscriptionTypeFormService {
  createSubscriptionTypeFormGroup(subscriptionType: SubscriptionTypeFormGroupInput = { id: null }): SubscriptionTypeFormGroup {
    const subscriptionTypeRawValue = {
      ...this.getFormDefaults(),
      ...subscriptionType,
    };
    return new FormGroup<SubscriptionTypeFormGroupContent>({
      id: new FormControl(
        { value: subscriptionTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      label: new FormControl(subscriptionTypeRawValue.label, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      summary: new FormControl(subscriptionTypeRawValue.summary, {
        validators: [Validators.maxLength(255)],
      }),
    });
  }

  getSubscriptionType(form: SubscriptionTypeFormGroup): ISubscriptionType | NewSubscriptionType {
    return form.getRawValue() as ISubscriptionType | NewSubscriptionType;
  }

  resetForm(form: SubscriptionTypeFormGroup, subscriptionType: SubscriptionTypeFormGroupInput): void {
    const subscriptionTypeRawValue = { ...this.getFormDefaults(), ...subscriptionType };
    form.reset(
      {
        ...subscriptionTypeRawValue,
        id: { value: subscriptionTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SubscriptionTypeFormDefaults {
    return {
      id: null,
    };
  }
}
