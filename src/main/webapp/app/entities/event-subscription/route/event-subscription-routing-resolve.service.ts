import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventSubscription } from '../event-subscription.model';
import { EventSubscriptionService } from '../service/event-subscription.service';

const eventSubscriptionResolve = (route: ActivatedRouteSnapshot): Observable<null | IEventSubscription> => {
  const id = route.params.id;
  if (id) {
    return inject(EventSubscriptionService)
      .find(id)
      .pipe(
        mergeMap((eventSubscription: HttpResponse<IEventSubscription>) => {
          if (eventSubscription.body) {
            return of(eventSubscription.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default eventSubscriptionResolve;
