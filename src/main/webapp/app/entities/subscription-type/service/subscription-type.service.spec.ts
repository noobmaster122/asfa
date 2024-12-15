import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISubscriptionType } from '../subscription-type.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../subscription-type.test-samples';

import { SubscriptionTypeService } from './subscription-type.service';

const requireRestSample: ISubscriptionType = {
  ...sampleWithRequiredData,
};

describe('SubscriptionType Service', () => {
  let service: SubscriptionTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubscriptionType | ISubscriptionType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SubscriptionTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SubscriptionType', () => {
      const subscriptionType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subscriptionType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubscriptionType', () => {
      const subscriptionType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subscriptionType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubscriptionType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubscriptionType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SubscriptionType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubscriptionTypeToCollectionIfMissing', () => {
      it('should add a SubscriptionType to an empty array', () => {
        const subscriptionType: ISubscriptionType = sampleWithRequiredData;
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing([], subscriptionType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionType);
      });

      it('should not add a SubscriptionType to an array that contains it', () => {
        const subscriptionType: ISubscriptionType = sampleWithRequiredData;
        const subscriptionTypeCollection: ISubscriptionType[] = [
          {
            ...subscriptionType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing(subscriptionTypeCollection, subscriptionType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubscriptionType to an array that doesn't contain it", () => {
        const subscriptionType: ISubscriptionType = sampleWithRequiredData;
        const subscriptionTypeCollection: ISubscriptionType[] = [sampleWithPartialData];
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing(subscriptionTypeCollection, subscriptionType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionType);
      });

      it('should add only unique SubscriptionType to an array', () => {
        const subscriptionTypeArray: ISubscriptionType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subscriptionTypeCollection: ISubscriptionType[] = [sampleWithRequiredData];
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing(subscriptionTypeCollection, ...subscriptionTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subscriptionType: ISubscriptionType = sampleWithRequiredData;
        const subscriptionType2: ISubscriptionType = sampleWithPartialData;
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing([], subscriptionType, subscriptionType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionType);
        expect(expectedResult).toContain(subscriptionType2);
      });

      it('should accept null and undefined values', () => {
        const subscriptionType: ISubscriptionType = sampleWithRequiredData;
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing([], null, subscriptionType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionType);
      });

      it('should return initial array if no SubscriptionType is added', () => {
        const subscriptionTypeCollection: ISubscriptionType[] = [sampleWithRequiredData];
        expectedResult = service.addSubscriptionTypeToCollectionIfMissing(subscriptionTypeCollection, undefined, null);
        expect(expectedResult).toEqual(subscriptionTypeCollection);
      });
    });

    describe('compareSubscriptionType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubscriptionType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubscriptionType(entity1, entity2);
        const compareResult2 = service.compareSubscriptionType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubscriptionType(entity1, entity2);
        const compareResult2 = service.compareSubscriptionType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubscriptionType(entity1, entity2);
        const compareResult2 = service.compareSubscriptionType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
