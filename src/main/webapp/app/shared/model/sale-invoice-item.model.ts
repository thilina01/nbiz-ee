import { ISaleInvoice } from 'app/shared/model//sale-invoice.model';
import { IPurchaseInvoiceItem } from 'app/shared/model//purchase-invoice-item.model';

export interface ISaleInvoiceItem {
    id?: number;
    sellingPrice?: number;
    discount?: number;
    quantity?: number;
    saleInvoice?: ISaleInvoice;
    purchaseInvoiceItem?: IPurchaseInvoiceItem;
}

export class SaleInvoiceItem implements ISaleInvoiceItem {
    constructor(
        public id?: number,
        public sellingPrice?: number,
        public discount?: number,
        public quantity?: number,
        public saleInvoice?: ISaleInvoice,
        public purchaseInvoiceItem?: IPurchaseInvoiceItem
    ) {}
}
