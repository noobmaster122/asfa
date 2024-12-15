import { IPaymentMethod, NewPaymentMethod } from './payment-method.model';

export const sampleWithRequiredData: IPaymentMethod = {
  id: 13676,
  label: 'sur de',
};

export const sampleWithPartialData: IPaymentMethod = {
  id: 13508,
  label: 'ouf',
};

export const sampleWithFullData: IPaymentMethod = {
  id: 17725,
  label: 'grâce à',
};

export const sampleWithNewData: NewPaymentMethod = {
  label: 'en plus de au-dedans de',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
