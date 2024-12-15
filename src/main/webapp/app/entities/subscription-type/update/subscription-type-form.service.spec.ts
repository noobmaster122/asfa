import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../subscription-type.test-samples';

import { SubscriptionTypeFormService } from './subscription-type-form.service';

describe('SubscriptionType Form Service', () => {
  let service: SubscriptionTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubscriptionTypeFormService);
  });

  describe('Service methods', () => {
    describe('createSubscriptionTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubscriptionTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            summary: expect.any(Object),
          }),
        );
      });

      it('passing ISubscriptionType should create a new form with FormGroup', () => {
        const formGroup = service.createSubscriptionTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            summary: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubscriptionType', () => {
      it('should return NewSubscriptionType for default SubscriptionType initial value', () => {
        const formGroup = service.createSubscriptionTypeFormGroup(sampleWithNewData);

        const subscriptionType = service.getSubscriptionType(formGroup) as any;

        expect(subscriptionType).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubscriptionType for empty SubscriptionType initial value', () => {
        const formGroup = service.createSubscriptionTypeFormGroup();

        const subscriptionType = service.getSubscriptionType(formGroup) as any;

        expect(subscriptionType).toMatchObject({});
      });

      it('should return ISubscriptionType', () => {
        const formGroup = service.createSubscriptionTypeFormGroup(sampleWithRequiredData);

        const subscriptionType = service.getSubscriptionType(formGroup) as any;

        expect(subscriptionType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubscriptionType should not enable id FormControl', () => {
        const formGroup = service.createSubscriptionTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubscriptionType should disable id FormControl', () => {
        const formGroup = service.createSubscriptionTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
