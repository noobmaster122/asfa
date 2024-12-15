import { ISubscriptionType, NewSubscriptionType } from './subscription-type.model';

export const sampleWithRequiredData: ISubscriptionType = {
  id: 26403,
  label: 'euh sous couleur de',
};

export const sampleWithPartialData: ISubscriptionType = {
  id: 4912,
  label: 'du moment que près employer',
  summary: 'de crainte que dense',
};

export const sampleWithFullData: ISubscriptionType = {
  id: 3744,
  label: 'au-dessous de',
  summary: 'sécher à peine',
};

export const sampleWithNewData: NewSubscriptionType = {
  label: 'équipe de recherche',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
