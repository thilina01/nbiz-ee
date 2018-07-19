import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    PurchaseInvoiceItemComponent,
    PurchaseInvoiceItemDetailComponent,
    PurchaseInvoiceItemUpdateComponent,
    PurchaseInvoiceItemDeletePopupComponent,
    PurchaseInvoiceItemDeleteDialogComponent,
    purchaseInvoiceItemRoute,
    purchaseInvoiceItemPopupRoute
} from './';

const ENTITY_STATES = [...purchaseInvoiceItemRoute, ...purchaseInvoiceItemPopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseInvoiceItemComponent,
        PurchaseInvoiceItemDetailComponent,
        PurchaseInvoiceItemUpdateComponent,
        PurchaseInvoiceItemDeleteDialogComponent,
        PurchaseInvoiceItemDeletePopupComponent
    ],
    entryComponents: [
        PurchaseInvoiceItemComponent,
        PurchaseInvoiceItemUpdateComponent,
        PurchaseInvoiceItemDeleteDialogComponent,
        PurchaseInvoiceItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEePurchaseInvoiceItemModule {}
