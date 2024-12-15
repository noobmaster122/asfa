export interface ICategory {
  id: number;
  label?: string | null;
  summary?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
