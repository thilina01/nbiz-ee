import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';
import { SaleInvoiceItemService } from './sale-invoice-item.service';
import { SaleInvoiceItemComponent } from './sale-invoice-item.component';
import { SaleInvoiceItemDetailComponent } from './sale-invoice-item-detail.component';
import { SaleInvoiceItemUpdateComponent } from './sale-invoice-item-update.component';
import { SaleInvoiceItemDeletePopupComponent } from './sale-invoice-item-delete-dialog.component';
import { ISaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

@Injectable({ providedIn: 'root' })
export class SaleInvoiceItemResolve implements Resolve<ISaleInvoiceItem> {
    constructor(private service: SaleInvoiceItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((saleInvoiceItem: HttpResponse<SaleInvoiceItem>) => saleInvoiceItem.body));
        }
        return of(new SaleInvoiceItem());
    }
}

export const saleInvoiceItemRoute: Routes = [
    {
        path: 'sale-invoice-item',
        component: SaleInvoiceItemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SaleInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-item/:id/view',
        component: SaleInvoiceItemDetailComponent,
        resolve: {
            saleInvoiceItem: SaleInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-item/new',
        component: SaleInvoiceItemUpdateComponent,
        resolve: {
            saleInvoiceItem: SaleInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-item/:id/edit',
        component: SaleInvoiceItemUpdateComponent,
        resolve: {
            saleInvoiceItem: SaleInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleInvoiceItemPopupRoute: Routes = [
    {
        path: 'sale-invoice-item/:id/delete',
        component: SaleInvoiceItemDeletePopupComponent,
        resolve: {
            saleInvoiceItem: SaleInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoiceItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
