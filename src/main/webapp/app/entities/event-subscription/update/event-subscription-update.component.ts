import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISubscriptionType } from 'app/entities/subscription-type/subscription-type.model';
import { SubscriptionTypeService } from 'app/entities/subscription-type/service/subscription-type.service';
import { IPayment } from 'app/entities/payment/payment.model';
import { PaymentService } from 'app/entities/payment/service/payment.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { EventSubscriptionService } from '../service/event-subscription.service';
import { IEventSubscription } from '../event-subscription.model';
import { EventSubscriptionFormGroup, EventSubscriptionFormService } from './event-subscription-form.service';

@Component({
  standalone: true,
  selector: 'jhi-event-subscription-update',
  templateUrl: './event-subscription-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EventSubscriptionUpdateComponent implements OnInit {
  isSaving = false;
  eventSubscription: IEventSubscription | null = null;

  subscriptionTypesSharedCollection: ISubscriptionType[] = [];
  paymentsSharedCollection: IPayment[] = [];
  membersSharedCollection: IMember[] = [];
  productsSharedCollection: IProduct[] = [];

  protected eventSubscriptionService = inject(EventSubscriptionService);
  protected eventSubscriptionFormService = inject(EventSubscriptionFormService);
  protected subscriptionTypeService = inject(SubscriptionTypeService);
  protected paymentService = inject(PaymentService);
  protected memberService = inject(MemberService);
  protected productService = inject(ProductService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EventSubscriptionFormGroup = this.eventSubscriptionFormService.createEventSubscriptionFormGroup();

  compareSubscriptionType = (o1: ISubscriptionType | null, o2: ISubscriptionType | null): boolean =>
    this.subscriptionTypeService.compareSubscriptionType(o1, o2);

  comparePayment = (o1: IPayment | null, o2: IPayment | null): boolean => this.paymentService.comparePayment(o1, o2);

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventSubscription }) => {
      this.eventSubscription = eventSubscription;
      if (eventSubscription) {
        this.updateForm(eventSubscription);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventSubscription = this.eventSubscriptionFormService.getEventSubscription(this.editForm);
    if (eventSubscription.id !== null) {
      this.subscribeToSaveResponse(this.eventSubscriptionService.update(eventSubscription));
    } else {
      this.subscribeToSaveResponse(this.eventSubscriptionService.create(eventSubscription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventSubscription>>): void {
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

  protected updateForm(eventSubscription: IEventSubscription): void {
    this.eventSubscription = eventSubscription;
    this.eventSubscriptionFormService.resetForm(this.editForm, eventSubscription);

    this.subscriptionTypesSharedCollection = this.subscriptionTypeService.addSubscriptionTypeToCollectionIfMissing<ISubscriptionType>(
      this.subscriptionTypesSharedCollection,
      eventSubscription.types,
    );
    this.paymentsSharedCollection = this.paymentService.addPaymentToCollectionIfMissing<IPayment>(
      this.paymentsSharedCollection,
      eventSubscription.payment,
    );
    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      ...(eventSubscription.members ?? []),
    );
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      ...(eventSubscription.products ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subscriptionTypeService
      .query()
      .pipe(map((res: HttpResponse<ISubscriptionType[]>) => res.body ?? []))
      .pipe(
        map((subscriptionTypes: ISubscriptionType[]) =>
          this.subscriptionTypeService.addSubscriptionTypeToCollectionIfMissing<ISubscriptionType>(
            subscriptionTypes,
            this.eventSubscription?.types,
          ),
        ),
      )
      .subscribe((subscriptionTypes: ISubscriptionType[]) => (this.subscriptionTypesSharedCollection = subscriptionTypes));

    this.paymentService
      .query()
      .pipe(map((res: HttpResponse<IPayment[]>) => res.body ?? []))
      .pipe(
        map((payments: IPayment[]) =>
          this.paymentService.addPaymentToCollectionIfMissing<IPayment>(payments, this.eventSubscription?.payment),
        ),
      )
      .subscribe((payments: IPayment[]) => (this.paymentsSharedCollection = payments));

    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(
        map((members: IMember[]) =>
          this.memberService.addMemberToCollectionIfMissing<IMember>(members, ...(this.eventSubscription?.members ?? [])),
        ),
      )
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing<IProduct>(products, ...(this.eventSubscription?.products ?? [])),
        ),
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
