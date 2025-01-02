import { Routes } from '@angular/router';
import { canActivateAuthRole } from './core/gurds/auth-role.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'products',
  },
  {
    path: 'products',
    loadComponent: () =>
      import(
        '../app/features/products/pages/product-list/product-list.component'
      ),
  },
  {
    path: 'orders',
    loadComponent: () =>
      import('../app/features/orders/pages/order-list/order-list.component'),
    canActivate: [canActivateAuthRole],
    // data: { role: 'user' },
  },
  {
    path: 'cart',
    loadComponent: () =>
      import('../app/features/cart/pages/cart/cart.component'),
    canActivate: [canActivateAuthRole],
    // data: { role: 'user' },
  },
];
