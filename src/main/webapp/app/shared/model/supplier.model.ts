import { IPurchaseInvoice } from 'app/shared/model//purchase-invoice.model';

export interface ISupplier {
    id?: number;
    code?: string;
    name?: string;
    purchaseInvoices?: IPurchaseInvoice[];
}

export class Supplier implements ISupplier {
    constructor(public id?: number, public code?: string, public name?: string, public purchaseInvoices?: IPurchaseInvoice[]) {}
}
