import { Moment } from 'moment';
import { IQuotationItem } from 'app/shared/model//quotation-item.model';
import { ICustomer } from 'app/shared/model//customer.model';

export interface IQuotation {
    id?: number;
    quotationDate?: Moment;
    amount?: number;
    discount?: number;
    quotationItems?: IQuotationItem[];
    customer?: ICustomer;
}

export class Quotation implements IQuotation {
    constructor(
        public id?: number,
        public quotationDate?: Moment,
        public amount?: number,
        public discount?: number,
        public quotationItems?: IQuotationItem[],
        public customer?: ICustomer
    ) {}
}
