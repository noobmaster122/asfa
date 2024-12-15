import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SubscriptionTypeService } from '../service/subscription-type.service';
import { ISubscriptionType } from '../subscription-type.model';
import { SubscriptionTypeFormService } from './subscription-type-form.service';

import { SubscriptionTypeUpdateComponent } from './subscription-type-update.component';

describe('SubscriptionType Management Update Component', () => {
  let comp: SubscriptionTypeUpdateComponent;
  let fixture: ComponentFixture<SubscriptionTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subscriptionTypeFormService: SubscriptionTypeFormService;
  let subscriptionTypeService: SubscriptionTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubscriptionTypeUpdateComponent],
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
      .overrideTemplate(SubscriptionTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubscriptionTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subscriptionTypeFormService = TestBed.inject(SubscriptionTypeFormService);
    subscriptionTypeService = TestBed.inject(SubscriptionTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const subscriptionType: ISubscriptionType = { id: 456 };

      activatedRoute.data = of({ subscriptionType });
      comp.ngOnInit();

      expect(comp.subscriptionType).toEqual(subscriptionType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubscriptionType>>();
      const subscriptionType = { id: 123 };
      jest.spyOn(subscriptionTypeFormService, 'getSubscriptionType').mockReturnValue(subscriptionType);
      jest.spyOn(subscriptionTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionType }));
      saveSubject.complete();

      // THEN
      expect(subscriptionTypeFormService.getSubscriptionType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subscriptionTypeService.update).toHaveBeenCalledWith(expect.objectContaining(subscriptionType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubscriptionType>>();
      const subscriptionType = { id: 123 };
      jest.spyOn(subscriptionTypeFormService, 'getSubscriptionType').mockReturnValue({ id: null });
      jest.spyOn(subscriptionTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionType }));
      saveSubject.complete();

      // THEN
      expect(subscriptionTypeFormService.getSubscriptionType).toHaveBeenCalled();
      expect(subscriptionTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubscriptionType>>();
      const subscriptionType = { id: 123 };
      jest.spyOn(subscriptionTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subscriptionTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
