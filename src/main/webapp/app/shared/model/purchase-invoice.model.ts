import { Moment } from 'moment';
import { IPurchaseInvoiceItem } from 'app/shared/model//purchase-invoice-item.model';
import { ISupplier } from 'app/shared/model//supplier.model';

export interface IPurchaseInvoice {
    id?: number;
    invoiceDate?: Moment;
    amount?: number;
    discount?: number;
    purchaseInvoiceItems?: IPurchaseInvoiceItem[];
    supplier?: ISupplier;
}

export class PurchaseInvoice implements IPurchaseInvoice {
    constructor(
        public id?: number,
        public invoiceDate?: Moment,
        public amount?: number,
        public discount?: number,
        public purchaseInvoiceItems?: IPurchaseInvoiceItem[],
        public supplier?: ISupplier
    ) {}
}
