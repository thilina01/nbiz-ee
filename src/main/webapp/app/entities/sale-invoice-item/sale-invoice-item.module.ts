import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    SaleInvoiceItemComponent,
    SaleInvoiceItemDetailComponent,
    SaleInvoiceItemUpdateComponent,
    SaleInvoiceItemDeletePopupComponent,
    SaleInvoiceItemDeleteDialogComponent,
    saleInvoiceItemRoute,
    saleInvoiceItemPopupRoute
} from './';

const ENTITY_STATES = [...saleInvoiceItemRoute, ...saleInvoiceItemPopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SaleInvoiceItemComponent,
        SaleInvoiceItemDetailComponent,
        SaleInvoiceItemUpdateComponent,
        SaleInvoiceItemDeleteDialogComponent,
        SaleInvoiceItemDeletePopupComponent
    ],
    entryComponents: [
        SaleInvoiceItemComponent,
        SaleInvoiceItemUpdateComponent,
        SaleInvoiceItemDeleteDialogComponent,
        SaleInvoiceItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeSaleInvoiceItemModule {}
