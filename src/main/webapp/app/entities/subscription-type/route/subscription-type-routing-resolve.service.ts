import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubscriptionType } from '../subscription-type.model';
import { SubscriptionTypeService } from '../service/subscription-type.service';

const subscriptionTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubscriptionType> => {
  const id = route.params.id;
  if (id) {
    return inject(SubscriptionTypeService)
      .find(id)
      .pipe(
        mergeMap((subscriptionType: HttpResponse<ISubscriptionType>) => {
          if (subscriptionType.body) {
            return of(subscriptionType.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default subscriptionTypeResolve;
