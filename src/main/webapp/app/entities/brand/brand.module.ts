import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NBizEeSharedModule } from 'app/shared';
import {
    BrandComponent,
    BrandDetailComponent,
    BrandUpdateComponent,
    BrandDeletePopupComponent,
    BrandDeleteDialogComponent,
    brandRoute,
    brandPopupRoute
} from './';

const ENTITY_STATES = [...brandRoute, ...brandPopupRoute];

@NgModule({
    imports: [NBizEeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BrandComponent, BrandDetailComponent, BrandUpdateComponent, BrandDeleteDialogComponent, BrandDeletePopupComponent],
    entryComponents: [BrandComponent, BrandUpdateComponent, BrandDeleteDialogComponent, BrandDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeBrandModule {}
