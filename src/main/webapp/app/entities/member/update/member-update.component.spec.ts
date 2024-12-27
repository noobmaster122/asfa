import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEventSubscription } from 'app/entities/event-subscription/event-subscription.model';
import { EventSubscriptionService } from 'app/entities/event-subscription/service/event-subscription.service';
import { MemberService } from '../service/member.service';
import { IMember } from '../member.model';
import { MemberFormService } from './member-form.service';

import { MemberUpdateComponent } from './member-update.component';

describe('Member Management Update Component', () => {
  let comp: MemberUpdateComponent;
  let fixture: ComponentFixture<MemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberFormService: MemberFormService;
  let memberService: MemberService;
  let eventSubscriptionService: EventSubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MemberUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberFormService = TestBed.inject(MemberFormService);
    memberService = TestBed.inject(MemberService);
    eventSubscriptionService = TestBed.inject(EventSubscriptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const member: IMember = { id: 456 };
      member.member = member;

      const memberCollection: IMember[] = [{ id: 12223 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ member });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining),
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSubscription query and add missing value', () => {
      const member: IMember = { id: 456 };
      const eventsubscriptions: IEventSubscription[] = [{ id: 24918 }];
      member.eventsubscriptions = eventsubscriptions;

      const eventSubscriptionCollection: IEventSubscription[] = [{ id: 10455 }];
      jest.spyOn(eventSubscriptionService, 'query').mockReturnValue(of(new HttpResponse({ body: eventSubscriptionCollection })));
      const additionalEventSubscriptions = [...eventsubscriptions];
      const expectedCollection: IEventSubscription[] = [...additionalEventSubscriptions, ...eventSubscriptionCollection];
      jest.spyOn(eventSubscriptionService, 'addEventSubscriptionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ member });
      comp.ngOnInit();

      expect(eventSubscriptionService.query).toHaveBeenCalled();
      expect(eventSubscriptionService.addEventSubscriptionToCollectionIfMissing).toHaveBeenCalledWith(
        eventSubscriptionCollection,
        ...additionalEventSubscriptions.map(expect.objectContaining),
      );
      expect(comp.eventSubscriptionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const member: IMember = { id: 456 };
      member.member = member;
      const eventsubscription: IEventSubscription = { id: 20848 };
      member.eventsubscriptions = [eventsubscription];

      activatedRoute.data = of({ member });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.eventSubscriptionsSharedCollection).toContain(eventsubscription);
      expect(comp.member).toEqual(member);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMember>>();
      const member = { id: 123 };
      jest.spyOn(memberFormService, 'getMember').mockReturnValue(member);
      jest.spyOn(memberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: member }));
      saveSubject.complete();

      // THEN
      expect(memberFormService.getMember).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberService.update).toHaveBeenCalledWith(expect.objectContaining(member));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMember>>();
      const member = { id: 123 };
      jest.spyOn(memberFormService, 'getMember').mockReturnValue({ id: null, memberUID: null });
      jest.spyOn(memberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: member }));
      saveSubject.complete();

      // THEN
      expect(memberFormService.getMember).toHaveBeenCalled();
      expect(memberService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMember>>();
      const member = { id: 123 };
      jest.spyOn(memberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEventSubscription', () => {
      it('Should forward to eventSubscriptionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventSubscriptionService, 'compareEventSubscription');
        comp.compareEventSubscription(entity, entity2);
        expect(eventSubscriptionService.compareEventSubscription).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
