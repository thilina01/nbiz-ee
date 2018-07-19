import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuotationItem } from 'app/shared/model/quotation-item.model';
import { QuotationItemService } from './quotation-item.service';
import { QuotationItemComponent } from './quotation-item.component';
import { QuotationItemDetailComponent } from './quotation-item-detail.component';
import { QuotationItemUpdateComponent } from './quotation-item-update.component';
import { QuotationItemDeletePopupComponent } from './quotation-item-delete-dialog.component';
import { IQuotationItem } from 'app/shared/model/quotation-item.model';

@Injectable({ providedIn: 'root' })
export class QuotationItemResolve implements Resolve<IQuotationItem> {
    constructor(private service: QuotationItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((quotationItem: HttpResponse<QuotationItem>) => quotationItem.body));
        }
        return of(new QuotationItem());
    }
}

export const quotationItemRoute: Routes = [
    {
        path: 'quotation-item',
        component: QuotationItemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'QuotationItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation-item/:id/view',
        component: QuotationItemDetailComponent,
        resolve: {
            quotationItem: QuotationItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation-item/new',
        component: QuotationItemUpdateComponent,
        resolve: {
            quotationItem: QuotationItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation-item/:id/edit',
        component: QuotationItemUpdateComponent,
        resolve: {
            quotationItem: QuotationItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationItemPopupRoute: Routes = [
    {
        path: 'quotation-item/:id/delete',
        component: QuotationItemDeletePopupComponent,
        resolve: {
            quotationItem: QuotationItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuotationItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
