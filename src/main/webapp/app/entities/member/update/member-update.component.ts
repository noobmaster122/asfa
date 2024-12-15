import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEventSubscription } from 'app/entities/event-subscription/event-subscription.model';
import { EventSubscriptionService } from 'app/entities/event-subscription/service/event-subscription.service';
import { FamilyRank } from 'app/entities/enumerations/family-rank.model';
import { MemberService } from '../service/member.service';
import { IMember } from '../member.model';
import { MemberFormGroup, MemberFormService } from './member-form.service';

@Component({
  standalone: true,
  selector: 'jhi-member-update',
  templateUrl: './member-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MemberUpdateComponent implements OnInit {
  isSaving = false;
  member: IMember | null = null;
  familyRankValues = Object.keys(FamilyRank);

  membersSharedCollection: IMember[] = [];
  eventSubscriptionsSharedCollection: IEventSubscription[] = [];

  protected memberService = inject(MemberService);
  protected memberFormService = inject(MemberFormService);
  protected eventSubscriptionService = inject(EventSubscriptionService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MemberFormGroup = this.memberFormService.createMemberFormGroup();

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareEventSubscription = (o1: IEventSubscription | null, o2: IEventSubscription | null): boolean =>
    this.eventSubscriptionService.compareEventSubscription(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ member }) => {
      this.member = member;
      if (member) {
        this.updateForm(member);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const member = this.memberFormService.getMember(this.editForm);
    /* eslint-disable no-console */
    console.log('am new member', member);
    /* eslint-enable no-console */
    if (member.id !== null) {
      this.subscribeToSaveResponse(this.memberService.update(member));
    } else {
      this.subscribeToSaveResponse(this.memberService.create(member));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>): void {
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

  protected updateForm(member: IMember): void {
    this.member = member;
    this.memberFormService.resetForm(this.editForm, member);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(this.membersSharedCollection, member.member);
    this.eventSubscriptionsSharedCollection = this.eventSubscriptionService.addEventSubscriptionToCollectionIfMissing<IEventSubscription>(
      this.eventSubscriptionsSharedCollection,
      ...(member.eventsubscriptions ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.member?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.eventSubscriptionService
      .query()
      .pipe(map((res: HttpResponse<IEventSubscription[]>) => res.body ?? []))
      .pipe(
        map((eventSubscriptions: IEventSubscription[]) =>
          this.eventSubscriptionService.addEventSubscriptionToCollectionIfMissing<IEventSubscription>(
            eventSubscriptions,
            ...(this.member?.eventsubscriptions ?? []),
          ),
        ),
      )
      .subscribe((eventSubscriptions: IEventSubscription[]) => (this.eventSubscriptionsSharedCollection = eventSubscriptions));
  }
}
