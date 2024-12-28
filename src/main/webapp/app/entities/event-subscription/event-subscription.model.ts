import dayjs from 'dayjs/esm';
import { ISubscriptionType } from 'app/entities/subscription-type/subscription-type.model';
import { IPayment } from 'app/entities/payment/payment.model';
import { IMember } from 'app/entities/member/member.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IEventSubscription {
  id: number;
  subscriptionDate?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  anonymousEmail?: string | null;
  anonymousName?: string | null;
  types?: Pick<ISubscriptionType, 'id'> | null;
  payment?: Pick<IPayment, 'id'> | null;
  members?: Pick<IMember, 'id'>[] | null;
  products?: Pick<IProduct, 'id'>[] | null;
}

export type NewEventSubscription = Omit<IEventSubscription, 'id'> & { id: null };
