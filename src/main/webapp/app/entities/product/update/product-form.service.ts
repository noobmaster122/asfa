import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IProduct, NewProduct } from '../product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduct for edit and NewProductFormGroupInput for create.
 */
type ProductFormGroupInput = IProduct | PartialWithRequiredKeyOf<NewProduct>;

type ProductFormDefaults = Pick<NewProduct, 'id' | 'eventsubscriptions'>;

type ProductFormGroupContent = {
  id: FormControl<IProduct['id'] | NewProduct['id']>;
  productUID: FormControl<IProduct['productUID']>;
  contractNumber: FormControl<IProduct['contractNumber']>;
  startDate: FormControl<IProduct['startDate']>;
  endDate: FormControl<IProduct['endDate']>;
  summary: FormControl<IProduct['summary']>;
  productType: FormControl<IProduct['productType']>;
  eventsubscriptions: FormControl<IProduct['eventsubscriptions']>;
  categories: FormControl<IProduct['categories']>;
};

export type ProductFormGroup = FormGroup<ProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductFormService {
  createProductFormGroup(product: ProductFormGroupInput = { id: null }): ProductFormGroup {
    const productRawValue = {
      ...this.getFormDefaults(),
      ...product,
    };
    return new FormGroup<ProductFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      productUID: new FormControl(productRawValue.productUID),
      contractNumber: new FormControl(productRawValue.contractNumber, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      startDate: new FormControl(productRawValue.startDate, {
        validators: [Validators.required],
      }),
      endDate: new FormControl(productRawValue.endDate),
      summary: new FormControl(productRawValue.summary, {
        validators: [Validators.maxLength(255)],
      }),
      productType: new FormControl(productRawValue.productType, {
        validators: [Validators.required],
      }),
      eventsubscriptions: new FormControl(productRawValue.eventsubscriptions ?? []),
      categories: new FormControl(productRawValue.categories),
    });
  }

  getProduct(form: ProductFormGroup): IProduct | NewProduct {
    return form.getRawValue() as IProduct | NewProduct;
  }

  resetForm(form: ProductFormGroup, product: ProductFormGroupInput): void {
    const productRawValue = { ...this.getFormDefaults(), ...product };
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProductFormDefaults {
    return {
      id: null,
      eventsubscriptions: [],
    };
  }
}
