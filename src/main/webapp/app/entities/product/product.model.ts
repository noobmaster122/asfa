import dayjs from 'dayjs/esm';
import { IEventSubscription } from 'app/entities/event-subscription/event-subscription.model';
import { ICategory } from 'app/entities/category/category.model';
import { ProductTypeEnum } from 'app/entities/enumerations/product-type-enum.model';

export interface IProduct {
  id: number;
  productUID?: string | null;
  contractNumber?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  summary?: string | null;
  productType?: keyof typeof ProductTypeEnum | null;
  eventsubscriptions?: Pick<IEventSubscription, 'id'>[] | null;
  categories?: Pick<ICategory, 'id'> | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
