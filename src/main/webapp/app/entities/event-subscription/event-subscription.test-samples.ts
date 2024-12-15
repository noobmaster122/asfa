import dayjs from 'dayjs/esm';

import { IEventSubscription, NewEventSubscription } from './event-subscription.model';

export const sampleWithRequiredData: IEventSubscription = {
  id: 31139,
  subscriptionDate: dayjs('2024-12-13'),
  isActive: false,
};

export const sampleWithPartialData: IEventSubscription = {
  id: 3719,
  subscriptionDate: dayjs('2024-12-13'),
  isActive: true,
};

export const sampleWithFullData: IEventSubscription = {
  id: 11316,
  subscriptionDate: dayjs('2024-12-13'),
  isActive: false,
};

export const sampleWithNewData: NewEventSubscription = {
  subscriptionDate: dayjs('2024-12-13'),
  isActive: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
