import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    SaleInvoicePaymentComponent,
    SaleInvoicePaymentDetailComponent,
    SaleInvoicePaymentUpdateComponent,
    SaleInvoicePaymentDeletePopupComponent,
    SaleInvoicePaymentDeleteDialogComponent,
    saleInvoicePaymentRoute,
    saleInvoicePaymentPopupRoute
} from './';

const ENTITY_STATES = [...saleInvoicePaymentRoute, ...saleInvoicePaymentPopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SaleInvoicePaymentComponent,
        SaleInvoicePaymentDetailComponent,
        SaleInvoicePaymentUpdateComponent,
        SaleInvoicePaymentDeleteDialogComponent,
        SaleInvoicePaymentDeletePopupComponent
    ],
    entryComponents: [
        SaleInvoicePaymentComponent,
        SaleInvoicePaymentUpdateComponent,
        SaleInvoicePaymentDeleteDialogComponent,
        SaleInvoicePaymentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeSaleInvoicePaymentModule {}
