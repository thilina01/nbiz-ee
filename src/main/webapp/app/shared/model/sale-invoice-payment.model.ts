import { Moment } from 'moment';
import { ISaleInvoice } from 'app/shared/model//sale-invoice.model';
import { IPaymentMethod } from 'app/shared/model//payment-method.model';

export interface ISaleInvoicePayment {
    id?: number;
    paymentDate?: Moment;
    amount?: number;
    saleInvoice?: ISaleInvoice;
    paymentMethod?: IPaymentMethod;
}

export class SaleInvoicePayment implements ISaleInvoicePayment {
    constructor(
        public id?: number,
        public paymentDate?: Moment,
        public amount?: number,
        public saleInvoice?: ISaleInvoice,
        public paymentMethod?: IPaymentMethod
    ) {}
}
