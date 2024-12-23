import { Component, inject, input } from '@angular/core';
import { Button } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { Product } from '../../interfaces/product.interface';
import { CartService } from '../../../cart/services/cart.service';

@Component({
  selector: 'app-product',
  imports: [CardModule, Button],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {

  book = input.required<Product>();
  cartService = inject(CartService);

  addToCart() {
    this.cartService.products().push(this.book());
  }

}
