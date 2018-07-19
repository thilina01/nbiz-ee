import { ISaleInvoice } from 'app/shared/model//sale-invoice.model';

export interface ISalesPerson {
    id?: number;
    code?: string;
    name?: string;
    saleInvoices?: ISaleInvoice[];
}

export class SalesPerson implements ISalesPerson {
    constructor(public id?: number, public code?: string, public name?: string, public saleInvoices?: ISaleInvoice[]) {}
}
