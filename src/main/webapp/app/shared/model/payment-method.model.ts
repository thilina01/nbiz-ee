import { ISaleInvoicePayment } from 'app/shared/model//sale-invoice-payment.model';

export interface IPaymentMethod {
    id?: number;
    code?: string;
    name?: string;
    saleInvoicePayments?: ISaleInvoicePayment[];
}

export class PaymentMethod implements IPaymentMethod {
    constructor(public id?: number, public code?: string, public name?: string, public saleInvoicePayments?: ISaleInvoicePayment[]) {}
}
