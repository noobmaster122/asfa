import dayjs from 'dayjs/esm';

import { IEventSubscription, NewEventSubscription } from './event-subscription.model';

export const sampleWithRequiredData: IEventSubscription = {
  id: 31730,
  subscriptionDate: dayjs('2024-12-12'),
  isActive: true,
};

export const sampleWithPartialData: IEventSubscription = {
  id: 16124,
  subscriptionDate: dayjs('2024-12-13'),
  isActive: true,
  anonymousEmail: 'secouriste davantage longtemps',
  anonymousName: 'si brûler sans que',
};

export const sampleWithFullData: IEventSubscription = {
  id: 17685,
  subscriptionDate: dayjs('2024-12-12'),
  isActive: true,
  anonymousEmail: "à l'entour de de sorte que",
  anonymousName: 'vite assez meuh',
};

export const sampleWithNewData: NewEventSubscription = {
  subscriptionDate: dayjs('2024-12-12'),
  isActive: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
