import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

export type PartialUpdatePaymentMethod = Partial<IPaymentMethod> & Pick<IPaymentMethod, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentMethod>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethod[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-methods');

  create(paymentMethod: NewPaymentMethod): Observable<EntityResponseType> {
    return this.http.post<IPaymentMethod>(this.resourceUrl, paymentMethod, { observe: 'response' });
  }

  update(paymentMethod: IPaymentMethod): Observable<EntityResponseType> {
    return this.http.put<IPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, paymentMethod, {
      observe: 'response',
    });
  }

  partialUpdate(paymentMethod: PartialUpdatePaymentMethod): Observable<EntityResponseType> {
    return this.http.patch<IPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, paymentMethod, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentMethodIdentifier(paymentMethod: Pick<IPaymentMethod, 'id'>): number {
    return paymentMethod.id;
  }

  comparePaymentMethod(o1: Pick<IPaymentMethod, 'id'> | null, o2: Pick<IPaymentMethod, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentMethodIdentifier(o1) === this.getPaymentMethodIdentifier(o2) : o1 === o2;
  }

  addPaymentMethodToCollectionIfMissing<Type extends Pick<IPaymentMethod, 'id'>>(
    paymentMethodCollection: Type[],
    ...paymentMethodsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentMethods: Type[] = paymentMethodsToCheck.filter(isPresent);
    if (paymentMethods.length > 0) {
      const paymentMethodCollectionIdentifiers = paymentMethodCollection.map(paymentMethodItem =>
        this.getPaymentMethodIdentifier(paymentMethodItem),
      );
      const paymentMethodsToAdd = paymentMethods.filter(paymentMethodItem => {
        const paymentMethodIdentifier = this.getPaymentMethodIdentifier(paymentMethodItem);
        if (paymentMethodCollectionIdentifiers.includes(paymentMethodIdentifier)) {
          return false;
        }
        paymentMethodCollectionIdentifiers.push(paymentMethodIdentifier);
        return true;
      });
      return [...paymentMethodsToAdd, ...paymentMethodCollection];
    }
    return paymentMethodCollection;
  }
}
