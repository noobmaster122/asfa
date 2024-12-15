import dayjs from 'dayjs/esm';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 13533,
  contractNumber: 'afin que si',
  startDate: dayjs('2024-12-13'),
  productType: 'REAL',
};

export const sampleWithPartialData: IProduct = {
  id: 21924,
  contractNumber: 'placide',
  startDate: dayjs('2024-12-13'),
  endDate: dayjs('2024-12-13'),
  productType: 'VIRTUAL',
};

export const sampleWithFullData: IProduct = {
  id: 1804,
  productUID: '45df08ed-2900-4896-bfbb-c2d3932cc7fa',
  contractNumber: 'mesurer foule commis',
  startDate: dayjs('2024-12-12'),
  endDate: dayjs('2024-12-13'),
  summary: 'a pin-pon',
  productType: 'REAL',
};

export const sampleWithNewData: NewProduct = {
  contractNumber: 'dynamique corps enseignant cadre',
  startDate: dayjs('2024-12-13'),
  productType: 'REAL',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
