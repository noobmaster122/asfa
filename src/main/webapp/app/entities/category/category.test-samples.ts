import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 12744,
  label: 'admirablement joliment',
};

export const sampleWithPartialData: ICategory = {
  id: 10895,
  label: 'à demi donner responsable',
};

export const sampleWithFullData: ICategory = {
  id: 13075,
  label: 'devant',
  summary: 'autrement critiquer à moins de',
};

export const sampleWithNewData: NewCategory = {
  label: 'au-dessous camper offrir',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
