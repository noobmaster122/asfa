import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISubscriptionType } from '../subscription-type.model';
import { SubscriptionTypeService } from '../service/subscription-type.service';
import { SubscriptionTypeFormGroup, SubscriptionTypeFormService } from './subscription-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-subscription-type-update',
  templateUrl: './subscription-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubscriptionTypeUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionType: ISubscriptionType | null = null;

  protected subscriptionTypeService = inject(SubscriptionTypeService);
  protected subscriptionTypeFormService = inject(SubscriptionTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SubscriptionTypeFormGroup = this.subscriptionTypeFormService.createSubscriptionTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionType }) => {
      this.subscriptionType = subscriptionType;
      if (subscriptionType) {
        this.updateForm(subscriptionType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscriptionType = this.subscriptionTypeFormService.getSubscriptionType(this.editForm);
    if (subscriptionType.id !== null) {
      this.subscribeToSaveResponse(this.subscriptionTypeService.update(subscriptionType));
    } else {
      this.subscribeToSaveResponse(this.subscriptionTypeService.create(subscriptionType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionType>>): void {
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

  protected updateForm(subscriptionType: ISubscriptionType): void {
    this.subscriptionType = subscriptionType;
    this.subscriptionTypeFormService.resetForm(this.editForm, subscriptionType);
  }
}
