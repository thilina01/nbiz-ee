import { IQuotation } from 'app/shared/model//quotation.model';
import { IPurchaseInvoiceItem } from 'app/shared/model//purchase-invoice-item.model';

export interface IQuotationItem {
    id?: number;
    amount?: number;
    discount?: number;
    quantity?: number;
    quotation?: IQuotation;
    purchaseInvoiceItem?: IPurchaseInvoiceItem;
}

export class QuotationItem implements IQuotationItem {
    constructor(
        public id?: number,
        public amount?: number,
        public discount?: number,
        public quantity?: number,
        public quotation?: IQuotation,
        public purchaseInvoiceItem?: IPurchaseInvoiceItem
    ) {}
}
