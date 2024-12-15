import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventSubscription, NewEventSubscription } from '../event-subscription.model';

export type PartialUpdateEventSubscription = Partial<IEventSubscription> & Pick<IEventSubscription, 'id'>;

type RestOf<T extends IEventSubscription | NewEventSubscription> = Omit<T, 'subscriptionDate'> & {
  subscriptionDate?: string | null;
};

export type RestEventSubscription = RestOf<IEventSubscription>;

export type NewRestEventSubscription = RestOf<NewEventSubscription>;

export type PartialUpdateRestEventSubscription = RestOf<PartialUpdateEventSubscription>;

export type EntityResponseType = HttpResponse<IEventSubscription>;
export type EntityArrayResponseType = HttpResponse<IEventSubscription[]>;

@Injectable({ providedIn: 'root' })
export class EventSubscriptionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-subscriptions');

  create(eventSubscription: NewEventSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventSubscription);
    return this.http
      .post<RestEventSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(eventSubscription: IEventSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventSubscription);
    return this.http
      .put<RestEventSubscription>(`${this.resourceUrl}/${this.getEventSubscriptionIdentifier(eventSubscription)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(eventSubscription: PartialUpdateEventSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventSubscription);
    return this.http
      .patch<RestEventSubscription>(`${this.resourceUrl}/${this.getEventSubscriptionIdentifier(eventSubscription)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEventSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEventSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventSubscriptionIdentifier(eventSubscription: Pick<IEventSubscription, 'id'>): number {
    return eventSubscription.id;
  }

  compareEventSubscription(o1: Pick<IEventSubscription, 'id'> | null, o2: Pick<IEventSubscription, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventSubscriptionIdentifier(o1) === this.getEventSubscriptionIdentifier(o2) : o1 === o2;
  }

  addEventSubscriptionToCollectionIfMissing<Type extends Pick<IEventSubscription, 'id'>>(
    eventSubscriptionCollection: Type[],
    ...eventSubscriptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventSubscriptions: Type[] = eventSubscriptionsToCheck.filter(isPresent);
    if (eventSubscriptions.length > 0) {
      const eventSubscriptionCollectionIdentifiers = eventSubscriptionCollection.map(eventSubscriptionItem =>
        this.getEventSubscriptionIdentifier(eventSubscriptionItem),
      );
      const eventSubscriptionsToAdd = eventSubscriptions.filter(eventSubscriptionItem => {
        const eventSubscriptionIdentifier = this.getEventSubscriptionIdentifier(eventSubscriptionItem);
        if (eventSubscriptionCollectionIdentifiers.includes(eventSubscriptionIdentifier)) {
          return false;
        }
        eventSubscriptionCollectionIdentifiers.push(eventSubscriptionIdentifier);
        return true;
      });
      return [...eventSubscriptionsToAdd, ...eventSubscriptionCollection];
    }
    return eventSubscriptionCollection;
  }

  protected convertDateFromClient<T extends IEventSubscription | NewEventSubscription | PartialUpdateEventSubscription>(
    eventSubscription: T,
  ): RestOf<T> {
    return {
      ...eventSubscription,
      subscriptionDate: eventSubscription.subscriptionDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEventSubscription: RestEventSubscription): IEventSubscription {
    return {
      ...restEventSubscription,
      subscriptionDate: restEventSubscription.subscriptionDate ? dayjs(restEventSubscription.subscriptionDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEventSubscription>): HttpResponse<IEventSubscription> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEventSubscription[]>): HttpResponse<IEventSubscription[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
