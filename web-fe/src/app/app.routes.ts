import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'products',
  },
  {
    path: 'products',
    pathMatch: 'full',
    loadComponent: () =>
      import(
        '../app/features/products/pages/product-list/product-list.component'
      ),
  },
];
