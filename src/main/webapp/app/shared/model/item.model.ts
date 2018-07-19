import { IPurchaseInvoiceItem } from 'app/shared/model//purchase-invoice-item.model';
import { IBrand } from 'app/shared/model//brand.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IItem {
    id?: number;
    code?: string;
    name?: string;
    model?: string;
    description?: string;
    reOrderLevel?: number;
    defaultPrice?: number;
    purchaseInvoiceItems?: IPurchaseInvoiceItem[];
    brand?: IBrand;
    category?: ICategory;
}

export class Item implements IItem {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public model?: string,
        public description?: string,
        public reOrderLevel?: number,
        public defaultPrice?: number,
        public purchaseInvoiceItems?: IPurchaseInvoiceItem[],
        public brand?: IBrand,
        public category?: ICategory
    ) {}
}
