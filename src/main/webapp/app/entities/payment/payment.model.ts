import dayjs from 'dayjs/esm';
import { IPaymentMethod } from 'app/entities/payment-method/payment-method.model';

export interface IPayment {
  id: number;
  paymentUID?: string | null;
  paymentDate?: dayjs.Dayjs | null;
  amount?: number | null;
  timeStamp?: dayjs.Dayjs | null;
  paymentmethods?: Pick<IPaymentMethod, 'id'> | null;
}

export type NewPayment = Omit<IPayment, 'id'> & { id: null };
