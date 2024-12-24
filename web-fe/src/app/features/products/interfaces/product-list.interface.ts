import { Pageable } from "../../../shared/interfaces/pageable.interface";
import { Sort } from "../../../shared/interfaces/sort.interface";
import { Product } from "./product.interface";

export interface ProductList {
    content: Product[];
    pageable: Pageable;
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    number: number;
    sort: Sort;
    numberOfElements: number;
    first: boolean;
    empty: boolean;
  }
