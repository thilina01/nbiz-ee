import { ISaleInvoice } from 'app/shared/model//sale-invoice.model';
import { IQuotation } from 'app/shared/model//quotation.model';

export interface ICustomer {
    id?: number;
    code?: string;
    name?: string;
    saleInvoices?: ISaleInvoice[];
    quotations?: IQuotation[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public saleInvoices?: ISaleInvoice[],
        public quotations?: IQuotation[]
    ) {}
}
