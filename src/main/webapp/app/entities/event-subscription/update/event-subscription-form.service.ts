import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEventSubscription, NewEventSubscription } from '../event-subscription.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventSubscription for edit and NewEventSubscriptionFormGroupInput for create.
 */
type EventSubscriptionFormGroupInput = IEventSubscription | PartialWithRequiredKeyOf<NewEventSubscription>;

type EventSubscriptionFormDefaults = Pick<NewEventSubscription, 'id' | 'isActive' | 'members' | 'products'>;

type EventSubscriptionFormGroupContent = {
  id: FormControl<IEventSubscription['id'] | NewEventSubscription['id']>;
  subscriptionDate: FormControl<IEventSubscription['subscriptionDate']>;
  isActive: FormControl<IEventSubscription['isActive']>;
  anonymousEmail: FormControl<IEventSubscription['anonymousEmail']>;
  anonymousName: FormControl<IEventSubscription['anonymousName']>;
  types: FormControl<IEventSubscription['types']>;
  payment: FormControl<IEventSubscription['payment']>;
  members: FormControl<IEventSubscription['members']>;
  products: FormControl<IEventSubscription['products']>;
};

export type EventSubscriptionFormGroup = FormGroup<EventSubscriptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventSubscriptionFormService {
  createEventSubscriptionFormGroup(eventSubscription: EventSubscriptionFormGroupInput = { id: null }): EventSubscriptionFormGroup {
    const eventSubscriptionRawValue = {
      ...this.getFormDefaults(),
      ...eventSubscription,
    };
    return new FormGroup<EventSubscriptionFormGroupContent>({
      id: new FormControl(
        { value: eventSubscriptionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      subscriptionDate: new FormControl(eventSubscriptionRawValue.subscriptionDate, {
        validators: [Validators.required],
      }),
      isActive: new FormControl(eventSubscriptionRawValue.isActive, {
        validators: [Validators.required],
      }),
      anonymousEmail: new FormControl(eventSubscriptionRawValue.anonymousEmail),
      anonymousName: new FormControl(eventSubscriptionRawValue.anonymousName, {
        validators: [Validators.maxLength(80)],
      }),
      types: new FormControl(eventSubscriptionRawValue.types),
      payment: new FormControl(eventSubscriptionRawValue.payment),
      members: new FormControl(eventSubscriptionRawValue.members ?? []),
      products: new FormControl(eventSubscriptionRawValue.products ?? []),
    });
  }

  getEventSubscription(form: EventSubscriptionFormGroup): IEventSubscription | NewEventSubscription {
    return form.getRawValue() as IEventSubscription | NewEventSubscription;
  }

  resetForm(form: EventSubscriptionFormGroup, eventSubscription: EventSubscriptionFormGroupInput): void {
    const eventSubscriptionRawValue = { ...this.getFormDefaults(), ...eventSubscription };
    form.reset(
      {
        ...eventSubscriptionRawValue,
        id: { value: eventSubscriptionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EventSubscriptionFormDefaults {
    return {
      id: null,
      isActive: false,
      members: [],
      products: [],
    };
  }
}
