import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'category',
    data: { pageTitle: 'Categories' },
    loadChildren: () => import('./category/category.routes'),
  },
  {
    path: 'event-subscription',
    data: { pageTitle: 'EventSubscriptions' },
    loadChildren: () => import('./event-subscription/event-subscription.routes'),
  },
  {
    path: 'member',
    data: { pageTitle: 'Members' },
    loadChildren: () => import('./member/member.routes'),
  },
  {
    path: 'payment',
    data: { pageTitle: 'Payments' },
    loadChildren: () => import('./payment/payment.routes'),
  },
  {
    path: 'payment-method',
    data: { pageTitle: 'PaymentMethods' },
    loadChildren: () => import('./payment-method/payment-method.routes'),
  },
  {
    path: 'product',
    data: { pageTitle: 'Products' },
    loadChildren: () => import('./product/product.routes'),
  },
  {
    path: 'subscription-type',
    data: { pageTitle: 'SubscriptionTypes' },
    loadChildren: () => import('./subscription-type/subscription-type.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
