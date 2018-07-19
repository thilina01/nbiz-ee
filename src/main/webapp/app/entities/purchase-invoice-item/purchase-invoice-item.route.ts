import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';
import { PurchaseInvoiceItemService } from './purchase-invoice-item.service';
import { PurchaseInvoiceItemComponent } from './purchase-invoice-item.component';
import { PurchaseInvoiceItemDetailComponent } from './purchase-invoice-item-detail.component';
import { PurchaseInvoiceItemUpdateComponent } from './purchase-invoice-item-update.component';
import { PurchaseInvoiceItemDeletePopupComponent } from './purchase-invoice-item-delete-dialog.component';
import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

@Injectable({ providedIn: 'root' })
export class PurchaseInvoiceItemResolve implements Resolve<IPurchaseInvoiceItem> {
    constructor(private service: PurchaseInvoiceItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((purchaseInvoiceItem: HttpResponse<PurchaseInvoiceItem>) => purchaseInvoiceItem.body));
        }
        return of(new PurchaseInvoiceItem());
    }
}

export const purchaseInvoiceItemRoute: Routes = [
    {
        path: 'purchase-invoice-item',
        component: PurchaseInvoiceItemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice-item/:id/view',
        component: PurchaseInvoiceItemDetailComponent,
        resolve: {
            purchaseInvoiceItem: PurchaseInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice-item/new',
        component: PurchaseInvoiceItemUpdateComponent,
        resolve: {
            purchaseInvoiceItem: PurchaseInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-invoice-item/:id/edit',
        component: PurchaseInvoiceItemUpdateComponent,
        resolve: {
            purchaseInvoiceItem: PurchaseInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoiceItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseInvoiceItemPopupRoute: Routes = [
    {
        path: 'purchase-invoice-item/:id/delete',
        component: PurchaseInvoiceItemDeletePopupComponent,
        resolve: {
            purchaseInvoiceItem: PurchaseInvoiceItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseInvoiceItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
