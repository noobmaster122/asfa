export interface IPaymentMethod {
  id: number;
  label?: string | null;
}

export type NewPaymentMethod = Omit<IPaymentMethod, 'id'> & { id: null };
