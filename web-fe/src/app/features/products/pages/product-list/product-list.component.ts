import { Component, inject } from '@angular/core';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { ImageModule } from 'primeng/image';
import { PaginatorModule } from 'primeng/paginator';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ProductComponent } from '../../components/product/product.component';

@Component({
  selector: 'app-product-list',
  imports: [
    PanelModule,
    CardModule,
    ImageModule,
    PaginatorModule,
    CommonModule,
    ProgressSpinnerModule,
    ProductComponent
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export default class ProductListComponent {

  productService = inject(ProductService);

  paginate(event: any) {
    const startIndex = event.first;
    this.productService.pageNumber.set(startIndex / event.rows);
    this.productService.pageSize.set(event.rows);
  }
}
