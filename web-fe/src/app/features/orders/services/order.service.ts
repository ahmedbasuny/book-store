import { inject, Injectable, signal } from '@angular/core';
import { OrderSummary } from '../interfaces/order-summary.interface';
import { rxResource } from '@angular/core/rxjs-interop';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  baseUrl = environment.baseUrl;
  http = inject(HttpClient);

  orders = rxResource<OrderSummary[], any>({
    loader: () =>
      this.http.get<OrderSummary[]>(`${this.baseUrl}/orders/api/v1/orders`),
  });
}
