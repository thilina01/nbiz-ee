import { Moment } from 'moment';
import { IPurchaseInvoice } from 'app/shared/model//purchase-invoice.model';
import { ISaleInvoiceItem } from 'app/shared/model//sale-invoice-item.model';
import { IQuotationItem } from 'app/shared/model//quotation-item.model';
import { IItem } from 'app/shared/model//item.model';

export interface IPurchaseInvoiceItem {
    id?: number;
    cost?: number;
    discount?: number;
    sellingPrice?: number;
    expiaryDate?: Moment;
    quantity?: number;
    serial?: string;
    purchaseInvoice?: IPurchaseInvoice;
    saleInvoiceItems?: ISaleInvoiceItem[];
    quotationItems?: IQuotationItem[];
    item?: IItem;
}

export class PurchaseInvoiceItem implements IPurchaseInvoiceItem {
    constructor(
        public id?: number,
        public cost?: number,
        public discount?: number,
        public sellingPrice?: number,
        public expiaryDate?: Moment,
        public quantity?: number,
        public serial?: string,
        public purchaseInvoice?: IPurchaseInvoice,
        public saleInvoiceItems?: ISaleInvoiceItem[],
        public quotationItems?: IQuotationItem[],
        public item?: IItem
    ) {}
}
