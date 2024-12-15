import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SubscriptionTypeResolve from './route/subscription-type-routing-resolve.service';

const subscriptionTypeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/subscription-type.component').then(m => m.SubscriptionTypeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/subscription-type-detail.component').then(m => m.SubscriptionTypeDetailComponent),
    resolve: {
      subscriptionType: SubscriptionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/subscription-type-update.component').then(m => m.SubscriptionTypeUpdateComponent),
    resolve: {
      subscriptionType: SubscriptionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/subscription-type-update.component').then(m => m.SubscriptionTypeUpdateComponent),
    resolve: {
      subscriptionType: SubscriptionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subscriptionTypeRoute;
