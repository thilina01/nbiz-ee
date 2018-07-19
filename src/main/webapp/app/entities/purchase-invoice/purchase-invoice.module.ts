import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    PurchaseInvoiceComponent,
    PurchaseInvoiceDetailComponent,
    PurchaseInvoiceUpdateComponent,
    PurchaseInvoiceDeletePopupComponent,
    PurchaseInvoiceDeleteDialogComponent,
    purchaseInvoiceRoute,
    purchaseInvoicePopupRoute
} from './';

const ENTITY_STATES = [...purchaseInvoiceRoute, ...purchaseInvoicePopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseInvoiceComponent,
        PurchaseInvoiceDetailComponent,
        PurchaseInvoiceUpdateComponent,
        PurchaseInvoiceDeleteDialogComponent,
        PurchaseInvoiceDeletePopupComponent
    ],
    entryComponents: [
        PurchaseInvoiceComponent,
        PurchaseInvoiceUpdateComponent,
        PurchaseInvoiceDeleteDialogComponent,
        PurchaseInvoiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEePurchaseInvoiceModule {}
