import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubscriptionType, NewSubscriptionType } from '../subscription-type.model';

export type PartialUpdateSubscriptionType = Partial<ISubscriptionType> & Pick<ISubscriptionType, 'id'>;

export type EntityResponseType = HttpResponse<ISubscriptionType>;
export type EntityArrayResponseType = HttpResponse<ISubscriptionType[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionTypeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subscription-types');

  create(subscriptionType: NewSubscriptionType): Observable<EntityResponseType> {
    return this.http.post<ISubscriptionType>(this.resourceUrl, subscriptionType, { observe: 'response' });
  }

  update(subscriptionType: ISubscriptionType): Observable<EntityResponseType> {
    return this.http.put<ISubscriptionType>(
      `${this.resourceUrl}/${this.getSubscriptionTypeIdentifier(subscriptionType)}`,
      subscriptionType,
      { observe: 'response' },
    );
  }

  partialUpdate(subscriptionType: PartialUpdateSubscriptionType): Observable<EntityResponseType> {
    return this.http.patch<ISubscriptionType>(
      `${this.resourceUrl}/${this.getSubscriptionTypeIdentifier(subscriptionType)}`,
      subscriptionType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubscriptionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubscriptionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubscriptionTypeIdentifier(subscriptionType: Pick<ISubscriptionType, 'id'>): number {
    return subscriptionType.id;
  }

  compareSubscriptionType(o1: Pick<ISubscriptionType, 'id'> | null, o2: Pick<ISubscriptionType, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubscriptionTypeIdentifier(o1) === this.getSubscriptionTypeIdentifier(o2) : o1 === o2;
  }

  addSubscriptionTypeToCollectionIfMissing<Type extends Pick<ISubscriptionType, 'id'>>(
    subscriptionTypeCollection: Type[],
    ...subscriptionTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subscriptionTypes: Type[] = subscriptionTypesToCheck.filter(isPresent);
    if (subscriptionTypes.length > 0) {
      const subscriptionTypeCollectionIdentifiers = subscriptionTypeCollection.map(subscriptionTypeItem =>
        this.getSubscriptionTypeIdentifier(subscriptionTypeItem),
      );
      const subscriptionTypesToAdd = subscriptionTypes.filter(subscriptionTypeItem => {
        const subscriptionTypeIdentifier = this.getSubscriptionTypeIdentifier(subscriptionTypeItem);
        if (subscriptionTypeCollectionIdentifiers.includes(subscriptionTypeIdentifier)) {
          return false;
        }
        subscriptionTypeCollectionIdentifiers.push(subscriptionTypeIdentifier);
        return true;
      });
      return [...subscriptionTypesToAdd, ...subscriptionTypeCollection];
    }
    return subscriptionTypeCollection;
  }
}
