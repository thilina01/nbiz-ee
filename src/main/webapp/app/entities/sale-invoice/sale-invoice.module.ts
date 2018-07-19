import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    SaleInvoiceComponent,
    SaleInvoiceDetailComponent,
    SaleInvoiceUpdateComponent,
    SaleInvoiceDeletePopupComponent,
    SaleInvoiceDeleteDialogComponent,
    saleInvoiceRoute,
    saleInvoicePopupRoute
} from './';

const ENTITY_STATES = [...saleInvoiceRoute, ...saleInvoicePopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SaleInvoiceComponent,
        SaleInvoiceDetailComponent,
        SaleInvoiceUpdateComponent,
        SaleInvoiceDeleteDialogComponent,
        SaleInvoiceDeletePopupComponent
    ],
    entryComponents: [SaleInvoiceComponent, SaleInvoiceUpdateComponent, SaleInvoiceDeleteDialogComponent, SaleInvoiceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeSaleInvoiceModule {}
