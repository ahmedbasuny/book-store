import { Injectable, resource, signal } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { ProductList } from '../interfaces/product-list.interface';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  baseUrl = environment.baseUrl;
  pageNumber = signal<number>(0);
  pageSize = signal<number>(10);

  constructor() {}

  booksPage = resource<ProductList, { pageNumber: number; pageSize: number }>({
    request: () => ({pageNumber: this.pageNumber() , pageSize: this.pageSize() }),
    loader: ({request}) => {
      return fetch(
        `${this.baseUrl}/catalog/api/v1/products?pageNumber=${request.pageNumber}&pageSize=${request.pageSize}`
      ).then((res) => res.json() );
    },
  });
}
