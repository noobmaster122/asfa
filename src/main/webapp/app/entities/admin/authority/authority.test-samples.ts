import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '4b39ae85-d081-434c-82ca-a3a44dcc5b77',
};

export const sampleWithPartialData: IAuthority = {
  name: '21c1b36d-a90a-4556-b581-2e8d7bd73361',
};

export const sampleWithFullData: IAuthority = {
  name: 'ae4c886b-692b-4182-a6ad-0d90a2297092',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
