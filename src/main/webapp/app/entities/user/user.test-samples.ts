import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 12732,
  login: '{ZOD@SVj2\\=yjot7\\Joq\\jV\\2EzuI',
};

export const sampleWithPartialData: IUser = {
  id: 14931,
  login: 'YEg@4BqbL\\PE-U7c\\TFTSdBF\\,hQC',
};

export const sampleWithFullData: IUser = {
  id: 13376,
  login: 'Oyg6',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
