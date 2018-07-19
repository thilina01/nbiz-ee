import { Moment } from 'moment';
import { ISaleInvoiceItem } from 'app/shared/model//sale-invoice-item.model';
import { ISaleInvoicePayment } from 'app/shared/model//sale-invoice-payment.model';
import { ICustomer } from 'app/shared/model//customer.model';
import { ISalesPerson } from 'app/shared/model//sales-person.model';

export interface ISaleInvoice {
    id?: number;
    invoiceDate?: Moment;
    amount?: number;
    paidAmount?: number;
    balanceAmount?: number;
    discount?: number;
    saleInvoiceItems?: ISaleInvoiceItem[];
    saleInvoices?: ISaleInvoicePayment[];
    customer?: ICustomer;
    salesPerson?: ISalesPerson;
}

export class SaleInvoice implements ISaleInvoice {
    constructor(
        public id?: number,
        public invoiceDate?: Moment,
        public amount?: number,
        public paidAmount?: number,
        public balanceAmount?: number,
        public discount?: number,
        public saleInvoiceItems?: ISaleInvoiceItem[],
        public saleInvoices?: ISaleInvoicePayment[],
        public customer?: ICustomer,
        public salesPerson?: ISalesPerson
    ) {}
}
