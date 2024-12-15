import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISubscriptionType } from 'app/entities/subscription-type/subscription-type.model';
import { SubscriptionTypeService } from 'app/entities/subscription-type/service/subscription-type.service';
import { IPayment } from 'app/entities/payment/payment.model';
import { PaymentService } from 'app/entities/payment/service/payment.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IEventSubscription } from '../event-subscription.model';
import { EventSubscriptionService } from '../service/event-subscription.service';
import { EventSubscriptionFormService } from './event-subscription-form.service';

import { EventSubscriptionUpdateComponent } from './event-subscription-update.component';

describe('EventSubscription Management Update Component', () => {
  let comp: EventSubscriptionUpdateComponent;
  let fixture: ComponentFixture<EventSubscriptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventSubscriptionFormService: EventSubscriptionFormService;
  let eventSubscriptionService: EventSubscriptionService;
  let subscriptionTypeService: SubscriptionTypeService;
  let paymentService: PaymentService;
  let memberService: MemberService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EventSubscriptionUpdateComponent],
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
      .overrideTemplate(EventSubscriptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventSubscriptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventSubscriptionFormService = TestBed.inject(EventSubscriptionFormService);
    eventSubscriptionService = TestBed.inject(EventSubscriptionService);
    subscriptionTypeService = TestBed.inject(SubscriptionTypeService);
    paymentService = TestBed.inject(PaymentService);
    memberService = TestBed.inject(MemberService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubscriptionType query and add missing value', () => {
      const eventSubscription: IEventSubscription = { id: 456 };
      const types: ISubscriptionType = { id: 20686 };
      eventSubscription.types = types;

      const subscriptionTypeCollection: ISubscriptionType[] = [{ id: 16738 }];
      jest.spyOn(subscriptionTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: subscriptionTypeCollection })));
      const additionalSubscriptionTypes = [types];
      const expectedCollection: ISubscriptionType[] = [...additionalSubscriptionTypes, ...subscriptionTypeCollection];
      jest.spyOn(subscriptionTypeService, 'addSubscriptionTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      expect(subscriptionTypeService.query).toHaveBeenCalled();
      expect(subscriptionTypeService.addSubscriptionTypeToCollectionIfMissing).toHaveBeenCalledWith(
        subscriptionTypeCollection,
        ...additionalSubscriptionTypes.map(expect.objectContaining),
      );
      expect(comp.subscriptionTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Payment query and add missing value', () => {
      const eventSubscription: IEventSubscription = { id: 456 };
      const payment: IPayment = { id: 12173 };
      eventSubscription.payment = payment;

      const paymentCollection: IPayment[] = [{ id: 6035 }];
      jest.spyOn(paymentService, 'query').mockReturnValue(of(new HttpResponse({ body: paymentCollection })));
      const additionalPayments = [payment];
      const expectedCollection: IPayment[] = [...additionalPayments, ...paymentCollection];
      jest.spyOn(paymentService, 'addPaymentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      expect(paymentService.query).toHaveBeenCalled();
      expect(paymentService.addPaymentToCollectionIfMissing).toHaveBeenCalledWith(
        paymentCollection,
        ...additionalPayments.map(expect.objectContaining),
      );
      expect(comp.paymentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Member query and add missing value', () => {
      const eventSubscription: IEventSubscription = { id: 456 };
      const members: IMember[] = [{ id: 13155 }];
      eventSubscription.members = members;

      const memberCollection: IMember[] = [{ id: 28841 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [...members];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining),
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const eventSubscription: IEventSubscription = { id: 456 };
      const products: IProduct[] = [{ id: 14972 }];
      eventSubscription.products = products;

      const productCollection: IProduct[] = [{ id: 25382 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [...products];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining),
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventSubscription: IEventSubscription = { id: 456 };
      const types: ISubscriptionType = { id: 23515 };
      eventSubscription.types = types;
      const payment: IPayment = { id: 29236 };
      eventSubscription.payment = payment;
      const member: IMember = { id: 3693 };
      eventSubscription.members = [member];
      const product: IProduct = { id: 10359 };
      eventSubscription.products = [product];

      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      expect(comp.subscriptionTypesSharedCollection).toContain(types);
      expect(comp.paymentsSharedCollection).toContain(payment);
      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.eventSubscription).toEqual(eventSubscription);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSubscription>>();
      const eventSubscription = { id: 123 };
      jest.spyOn(eventSubscriptionFormService, 'getEventSubscription').mockReturnValue(eventSubscription);
      jest.spyOn(eventSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventSubscription }));
      saveSubject.complete();

      // THEN
      expect(eventSubscriptionFormService.getEventSubscription).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventSubscriptionService.update).toHaveBeenCalledWith(expect.objectContaining(eventSubscription));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSubscription>>();
      const eventSubscription = { id: 123 };
      jest.spyOn(eventSubscriptionFormService, 'getEventSubscription').mockReturnValue({ id: null });
      jest.spyOn(eventSubscriptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSubscription: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventSubscription }));
      saveSubject.complete();

      // THEN
      expect(eventSubscriptionFormService.getEventSubscription).toHaveBeenCalled();
      expect(eventSubscriptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSubscription>>();
      const eventSubscription = { id: 123 };
      jest.spyOn(eventSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventSubscriptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSubscriptionType', () => {
      it('Should forward to subscriptionTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subscriptionTypeService, 'compareSubscriptionType');
        comp.compareSubscriptionType(entity, entity2);
        expect(subscriptionTypeService.compareSubscriptionType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePayment', () => {
      it('Should forward to paymentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paymentService, 'comparePayment');
        comp.comparePayment(entity, entity2);
        expect(paymentService.comparePayment).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
