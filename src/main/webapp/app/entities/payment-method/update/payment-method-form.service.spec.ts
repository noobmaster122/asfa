import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../payment-method.test-samples';

import { PaymentMethodFormService } from './payment-method-form.service';

describe('PaymentMethod Form Service', () => {
  let service: PaymentMethodFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentMethodFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentMethodFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentMethodFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
          }),
        );
      });

      it('passing IPaymentMethod should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentMethodFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
          }),
        );
      });
    });

    describe('getPaymentMethod', () => {
      it('should return NewPaymentMethod for default PaymentMethod initial value', () => {
        const formGroup = service.createPaymentMethodFormGroup(sampleWithNewData);

        const paymentMethod = service.getPaymentMethod(formGroup) as any;

        expect(paymentMethod).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentMethod for empty PaymentMethod initial value', () => {
        const formGroup = service.createPaymentMethodFormGroup();

        const paymentMethod = service.getPaymentMethod(formGroup) as any;

        expect(paymentMethod).toMatchObject({});
      });

      it('should return IPaymentMethod', () => {
        const formGroup = service.createPaymentMethodFormGroup(sampleWithRequiredData);

        const paymentMethod = service.getPaymentMethod(formGroup) as any;

        expect(paymentMethod).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentMethod should not enable id FormControl', () => {
        const formGroup = service.createPaymentMethodFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentMethod should disable id FormControl', () => {
        const formGroup = service.createPaymentMethodFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
