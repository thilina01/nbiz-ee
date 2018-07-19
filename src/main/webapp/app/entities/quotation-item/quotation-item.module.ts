import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    QuotationItemComponent,
    QuotationItemDetailComponent,
    QuotationItemUpdateComponent,
    QuotationItemDeletePopupComponent,
    QuotationItemDeleteDialogComponent,
    quotationItemRoute,
    quotationItemPopupRoute
} from './';

const ENTITY_STATES = [...quotationItemRoute, ...quotationItemPopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuotationItemComponent,
        QuotationItemDetailComponent,
        QuotationItemUpdateComponent,
        QuotationItemDeleteDialogComponent,
        QuotationItemDeletePopupComponent
    ],
    entryComponents: [
        QuotationItemComponent,
        QuotationItemUpdateComponent,
        QuotationItemDeleteDialogComponent,
        QuotationItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeQuotationItemModule {}
