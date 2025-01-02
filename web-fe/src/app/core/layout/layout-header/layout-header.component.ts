import { Component, inject, ViewEncapsulation } from '@angular/core';
import { BadgeModule } from 'primeng/badge';
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { InputTextModule } from 'primeng/inputtext';
import { CommonModule } from '@angular/common';
import { Ripple } from 'primeng/ripple';
import { MenuItem } from 'primeng/api';
import { ImageModule } from 'primeng/image';
import { CartService } from '../../../features/cart/services/cart.service';
import { Router } from '@angular/router';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-layout-header',
  imports: [
    MenubarModule,
    BadgeModule,
    AvatarModule,
    InputTextModule,
    Ripple,
    CommonModule,
    ImageModule,
  ],
  templateUrl: './layout-header.component.html',
  styleUrl: './layout-header.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class LayoutHeaderComponent {
  cartService = inject(CartService);
  router = inject(Router);
  keycloak = inject(Keycloak);

  ngOnInit() {
    console.log('token ==>', this.keycloak.token);
    
  }

  onProductsClick() {
    this.router.navigate(['products']);
  }
  onOrdersClick() {
    this.router.navigate(['orders']);
  }

  onCartClick() {
    this.router.navigate(['cart']);
  }

  onLoginClick() {
    this.keycloak.login();
  }

  onLogoutClick() {
    this.keycloak.logout();
  }
}
