import dayjs from 'dayjs/esm';

import { IMember, NewMember } from './member.model';

export const sampleWithRequiredData: IMember = {
  id: 29530,
  firstName: 'Parfait',
  lastName: 'Royer',
  email: 'Amalric60@gmail.com',
  country: 'Turkménistan',
  city: 'Cholet',
  address: 'broum',
  zipCode: '39197',
  birthDate: dayjs('2024-12-13'),
  signupDate: dayjs('2024-12-13'),
  rank: 'SPOUSE',
};

export const sampleWithPartialData: IMember = {
  id: 10866,
  firstName: 'Paterne',
  lastName: 'Louis',
  middleName: 'grandement membre titulaire tard',
  email: 'Eve.Garcia85@hotmail.fr',
  country: 'Koweït',
  city: 'Colmar',
  address: 'grandement ouah apprécier',
  zipCode: '16523',
  birthDate: dayjs('2024-12-13'),
  signupDate: dayjs('2024-12-13'),
  rank: 'PRIMARY_GUARDIAN',
};

export const sampleWithFullData: IMember = {
  id: 22447,
  memberUID: null,
  firstName: 'Berthe',
  lastName: 'Robert',
  middleName: 'chef pendant que autrefois',
  email: 'Armance29@gmail.com',
  country: 'Djibouti',
  city: 'Toulouse',
  address: 'tant de façon à ce que vétuste',
  zipCode: '27474',
  birthDate: dayjs('2024-12-13'),
  signupDate: dayjs('2024-12-13'),
  rank: 'SPOUSE',
};

export const sampleWithNewData: NewMember = {
  memberUID: null,
  firstName: 'Gontran',
  lastName: 'Bonnet',
  email: 'Stephanie97@yahoo.fr',
  country: 'Palau',
  city: 'Saint-Nazaire',
  address: 'vide garantir',
  zipCode: '25647',
  birthDate: dayjs('2024-12-13'),
  signupDate: dayjs('2024-12-13'),
  rank: 'SPOUSE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
