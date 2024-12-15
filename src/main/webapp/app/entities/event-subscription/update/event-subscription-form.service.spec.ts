import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../event-subscription.test-samples';

import { EventSubscriptionFormService } from './event-subscription-form.service';

describe('EventSubscription Form Service', () => {
  let service: EventSubscriptionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventSubscriptionFormService);
  });

  describe('Service methods', () => {
    describe('createEventSubscriptionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventSubscriptionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            subscriptionDate: expect.any(Object),
            isActive: expect.any(Object),
            types: expect.any(Object),
            payment: expect.any(Object),
            members: expect.any(Object),
            products: expect.any(Object),
          }),
        );
      });

      it('passing IEventSubscription should create a new form with FormGroup', () => {
        const formGroup = service.createEventSubscriptionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            subscriptionDate: expect.any(Object),
            isActive: expect.any(Object),
            types: expect.any(Object),
            payment: expect.any(Object),
            members: expect.any(Object),
            products: expect.any(Object),
          }),
        );
      });
    });

    describe('getEventSubscription', () => {
      it('should return NewEventSubscription for default EventSubscription initial value', () => {
        const formGroup = service.createEventSubscriptionFormGroup(sampleWithNewData);

        const eventSubscription = service.getEventSubscription(formGroup) as any;

        expect(eventSubscription).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventSubscription for empty EventSubscription initial value', () => {
        const formGroup = service.createEventSubscriptionFormGroup();

        const eventSubscription = service.getEventSubscription(formGroup) as any;

        expect(eventSubscription).toMatchObject({});
      });

      it('should return IEventSubscription', () => {
        const formGroup = service.createEventSubscriptionFormGroup(sampleWithRequiredData);

        const eventSubscription = service.getEventSubscription(formGroup) as any;

        expect(eventSubscription).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventSubscription should not enable id FormControl', () => {
        const formGroup = service.createEventSubscriptionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventSubscription should disable id FormControl', () => {
        const formGroup = service.createEventSubscriptionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
