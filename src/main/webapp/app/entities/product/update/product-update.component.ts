import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEventSubscription } from 'app/entities/event-subscription/event-subscription.model';
import { EventSubscriptionService } from 'app/entities/event-subscription/service/event-subscription.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { ProductTypeEnum } from 'app/entities/enumerations/product-type-enum.model';
import { ProductService } from '../service/product.service';
import { IProduct } from '../product.model';
import { ProductFormGroup, ProductFormService } from './product-form.service';

@Component({
  standalone: true,
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  product: IProduct | null = null;
  productTypeEnumValues = Object.keys(ProductTypeEnum);

  eventSubscriptionsSharedCollection: IEventSubscription[] = [];
  categoriesSharedCollection: ICategory[] = [];

  protected productService = inject(ProductService);
  protected productFormService = inject(ProductFormService);
  protected eventSubscriptionService = inject(EventSubscriptionService);
  protected categoryService = inject(CategoryService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProductFormGroup = this.productFormService.createProductFormGroup();

  compareEventSubscription = (o1: IEventSubscription | null, o2: IEventSubscription | null): boolean =>
    this.eventSubscriptionService.compareEventSubscription(o1, o2);

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      if (product) {
        this.updateForm(product);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.productFormService.getProduct(this.editForm);
    if (product.id !== null) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(product: IProduct): void {
    this.product = product;
    this.productFormService.resetForm(this.editForm, product);

    this.eventSubscriptionsSharedCollection = this.eventSubscriptionService.addEventSubscriptionToCollectionIfMissing<IEventSubscription>(
      this.eventSubscriptionsSharedCollection,
      ...(product.eventsubscriptions ?? []),
    );
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      product.categories,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventSubscriptionService
      .query()
      .pipe(map((res: HttpResponse<IEventSubscription[]>) => res.body ?? []))
      .pipe(
        map((eventSubscriptions: IEventSubscription[]) =>
          this.eventSubscriptionService.addEventSubscriptionToCollectionIfMissing<IEventSubscription>(
            eventSubscriptions,
            ...(this.product?.eventsubscriptions ?? []),
          ),
        ),
      )
      .subscribe((eventSubscriptions: IEventSubscription[]) => (this.eventSubscriptionsSharedCollection = eventSubscriptions));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.product?.categories),
        ),
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));
  }
}
