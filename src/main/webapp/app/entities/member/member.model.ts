import dayjs from 'dayjs/esm';
import { IEventSubscription } from 'app/entities/event-subscription/event-subscription.model';
import { FamilyRank } from 'app/entities/enumerations/family-rank.model';

export interface IMember {
  id: number;
  memberUID?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  middleName?: string | null;
  email?: string | null;
  country?: string | null;
  city?: string | null;
  address?: string | null;
  zipCode?: string | null;
  birthDate?: dayjs.Dayjs | null;
  signupDate?: dayjs.Dayjs | null;
  rank?: keyof typeof FamilyRank | null;
  eventsubscriptions?: Pick<IEventSubscription, 'id'>[] | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMember = Omit<IMember, 'id'> & { id: null };
