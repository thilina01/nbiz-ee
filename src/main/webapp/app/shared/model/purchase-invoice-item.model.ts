import { Moment } from 'moment';
import { ISaleInvoiceItem } from 'app/shared/model//sale-invoice-item.model';
import { IQuotationItem } from 'app/shared/model//quotation-item.model';
import { IPurchaseInvoice } from 'app/shared/model//purchase-invoice.model';
import { IItem } from 'app/shared/model//item.model';

export interface IPurchaseInvoiceItem {
    id?: number;
    cost?: number;
    discount?: number;
    sellingPrice?: number;
    expiaryDate?: Moment;
    quantity?: number;
    serial?: string;
    saleInvoiceItems?: ISaleInvoiceItem[];
    quotationItems?: IQuotationItem[];
    purchaseInvoice?: IPurchaseInvoice;
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
        public saleInvoiceItems?: ISaleInvoiceItem[],
        public quotationItems?: IQuotationItem[],
        public purchaseInvoice?: IPurchaseInvoice,
        public item?: IItem
    ) {}
}
