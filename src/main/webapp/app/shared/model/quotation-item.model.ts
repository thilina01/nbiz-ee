import { IQuotation } from 'app/shared/model//quotation.model';
import { IPurchaseInvoiceItem } from 'app/shared/model//purchase-invoice-item.model';

export interface IQuotationItem {
    id?: number;
    sellingPrice?: number;
    discount?: number;
    quantity?: number;
    quotation?: IQuotation;
    purchaseInvoiceItem?: IPurchaseInvoiceItem;
}

export class QuotationItem implements IQuotationItem {
    constructor(
        public id?: number,
        public sellingPrice?: number,
        public discount?: number,
        public quantity?: number,
        public quotation?: IQuotation,
        public purchaseInvoiceItem?: IPurchaseInvoiceItem
    ) {}
}
