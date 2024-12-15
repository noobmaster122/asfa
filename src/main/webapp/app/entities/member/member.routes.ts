import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MemberResolve from './route/member-routing-resolve.service';

const memberRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/member.component').then(m => m.MemberComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/member-detail.component').then(m => m.MemberDetailComponent),
    resolve: {
      member: MemberResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/member-update.component').then(m => m.MemberUpdateComponent),
    resolve: {
      member: MemberResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/member-update.component').then(m => m.MemberUpdateComponent),
    resolve: {
      member: MemberResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default memberRoute;
