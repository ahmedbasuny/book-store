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
onCartClick() {
throw new Error('Method not implemented.');
}
  cartService = inject(CartService);

  ngOnInit() {
  }
}
