import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EventSubscriptionResolve from './route/event-subscription-routing-resolve.service';

const eventSubscriptionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/event-subscription.component').then(m => m.EventSubscriptionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/event-subscription-detail.component').then(m => m.EventSubscriptionDetailComponent),
    resolve: {
      eventSubscription: EventSubscriptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/event-subscription-update.component').then(m => m.EventSubscriptionUpdateComponent),
    resolve: {
      eventSubscription: EventSubscriptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/event-subscription-update.component').then(m => m.EventSubscriptionUpdateComponent),
    resolve: {
      eventSubscription: EventSubscriptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default eventSubscriptionRoute;
