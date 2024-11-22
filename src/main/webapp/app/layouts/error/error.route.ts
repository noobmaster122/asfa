import { Routes } from '@angular/router';

export const errorRoute: Routes = [
  {
    path: 'error',
    loadComponent: () => import('./error.component'),
    title: "Page d'erreur !",
  },
  {
    path: 'accessdenied',
    loadComponent: () => import('./error.component'),
    data: {
      errorMessage: "Vous n'avez pas les droits pour accéder à cette page.",
    },
    title: "Page d'erreur !",
  },
  {
    path: '404',
    loadComponent: () => import('./error.component'),
    data: {
      errorMessage: "La page n'existe pas.",
    },
    title: "Page d'erreur !",
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];
