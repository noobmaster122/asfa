export interface ISubscriptionType {
  id: number;
  label?: string | null;
  summary?: string | null;
}

export type NewSubscriptionType = Omit<ISubscriptionType, 'id'> & { id: null };
