import { Injectable, signal } from '@angular/core';
import { Product } from '../../products/interfaces/product.interface';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  products = signal<Product[]>([]);
  
  constructor() { }
}
